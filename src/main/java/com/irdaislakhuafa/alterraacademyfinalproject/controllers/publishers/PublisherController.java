package com.irdaislakhuafa.alterraacademyfinalproject.controllers.publishers;

import java.util.Optional;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.PublisherDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.PublisherDtoWithoutAddress;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.AddressService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.PublisherService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = { "/api/v1/publishers" })
public class PublisherController {
    private final PublisherService publisherService;
    private final ApiValidation apiValidation;
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PublisherDto publisherDto, Errors errors) {
        ApiResponse<?> apiResponse = null;

        if (errors.hasErrors()) {
            apiResponse = ApiResponse.validationFailed(this.apiValidation.getErrorMessages(errors));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        try {
            var publisher = publisherService.mapToEntity(publisherDto);
            this.addressService.save(publisher.getAddress());
            var savedPublisher = publisherService.save(publisher);
            apiResponse = ApiResponse.success(savedPublisher);

            return ResponseEntity.ok().body(apiResponse);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            apiResponse = ApiResponse.error(e.getMessage());
            return ResponseEntity.internalServerError().body(apiResponse);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Request find all publishers");
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;
        try {
            var publisher = publisherService.findAll();
            apiResponse = ApiResponse.success(publisher);
            responses = ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            apiResponse = ApiResponse.error(e.getMessage());
            responses = ResponseEntity.internalServerError().body(apiResponse);
        }

        return responses;
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody @Valid ApiChangeRequests<PublisherDtoWithoutAddress> updateRequest,
            Errors errors) {
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;

        if (errors.hasErrors()) {
            log.error("Error validation");
            apiResponse = ApiResponse.validationFailed(this.apiValidation.getErrorMessages(errors));
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            try {
                var publisher = publisherService.findById(updateRequest.getTargetId());
                if (!publisher.isPresent()) {
                    apiResponse = ApiResponse.failed("data with id: " + updateRequest.getTargetId() + " not found");
                    responses = ResponseEntity.badRequest().body(apiResponse);

                } else {
                    var address = publisher.get().getAddress();
                    publisher = Optional.of(
                            Publisher.builder()
                                    .name(updateRequest.getData().getName())
                                    .email(updateRequest.getData().getEmail())
                                    .address(address)
                                    .build());

                    publisher.get().setId(updateRequest.getTargetId());

                    var updatedPublisher = publisherService.update(publisher.get());
                    apiResponse = ApiResponse.success(updatedPublisher);
                    responses = ResponseEntity.ok().body(apiResponse);
                }
            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.error(e.getMessage());
                responses = ResponseEntity.internalServerError().body(apiResponse);
            }
        }

        return responses;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(
            @RequestBody @Valid ApiTargetIdRequest deleteRequests,
            Errors errors) {

        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;

        log.info("Request delete publisher by id");
        if (errors.hasErrors()) {
            apiResponse = ApiResponse.validationFailed(apiValidation.getErrorMessages(errors));
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            log.info("Validation is valid");
            try {
                var targetDeleted = publisherService.findById(deleteRequests.getTargetId());
                if (!targetDeleted.isPresent()) {
                    log.debug("Publisher with id: " + deleteRequests.getTargetId() + " not found");
                    apiResponse = ApiResponse
                            .failed("publisher with id: " + deleteRequests.getTargetId() + " not found");
                    responses = ResponseEntity.badRequest().body(apiResponse);

                } else {
                    publisherService.deleteById(deleteRequests.getTargetId());
                    apiResponse = ApiResponse.success(targetDeleted);
                    responses = ResponseEntity.ok().body(apiResponse);
                    log.info("Success deleted publisher id: " + targetDeleted.get().getId());
                }

            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.error(e.getMessage());
                responses = ResponseEntity.internalServerError().body(apiResponse);
            }
        }
        return responses;
    }

    @GetMapping(value = { "/findBy/id" })
    public ResponseEntity<?> findById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;

        if (errors.hasErrors()) {
            apiResponse = ApiResponse.validationFailed(apiValidation.getErrorMessages(errors));
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            log.error("Validation is valid");
            try {
                var publisher = publisherService.findById(request.getTargetId());
                var isPresent = publisher.isPresent();

                apiResponse = ApiResponse.builder()
                        .message((isPresent) ? ApiMessage.SUCCESS : ApiMessage.FAILED)
                        .data((isPresent) ? publisher.get() : null)
                        .error((isPresent) ? null : "publisher with id: " + request.getTargetId() + " not found")
                        .build();

                responses = new ResponseEntity<>(apiResponse, (isPresent) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.error(e.getMessage());
                responses = ResponseEntity.internalServerError().body(apiResponse);
            }
        }

        return responses;
    }
}