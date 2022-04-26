package com.irdaislakhuafa.alterraacademyfinalproject.controllers.authors;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AddressService;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AuthorService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiMessage;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = { "/authors/address" })
public class AuthorAddressController {
    private final AuthorService authorService;
    private final AddressService addressService;
    public final ApiValidation apiValidation;

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody @Valid ApiChangeRequests<List<AddressDto>> requests,
            Errors errors) {

        ApiResponse<?> apiResponse = null;

        log.info("Request add address for author");
        if (errors.hasErrors()) {
            log.warn("Validation error");
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(this.apiValidation.getErrorMessages(errors))
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);

        } else {
            log.info("Validation is valid");
            try {
                var author = authorService.findById(requests.getTargetId());

                if (!author.isPresent()) {
                    log.info("Author with id: " + requests.getTargetId() + " not found");
                    apiResponse = ApiResponse.builder()
                            .message(ApiMessage.FAILED)
                            .error("Author with id: " + requests.getTargetId() + " not found")
                            .data(null)
                            .build();
                    return ResponseEntity.badRequest().body(apiResponse);
                } else {
                    log.info("Saving all address");
                    var savedAddress = addressService.saveAll(addressService.mapToEntities(requests.getData()));
                    author.get().getAddress().addAll(savedAddress);

                    log.info("Updating author address");
                    author = this.authorService.save(author.get());

                    apiResponse = ApiResponse.builder()
                            .message(ApiMessage.SUCCESS)
                            .error(null)
                            .data(author)
                            .build();
                    return ResponseEntity.ok().body(apiResponse);
                }
            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.builder()
                        .message(ApiMessage.ERROR)
                        .error(e.getMessage())
                        .data(null)
                        .build();
                return ResponseEntity.internalServerError().body(apiResponse);
            }
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ApiChangeRequests<AddressDto> requests, Errors errors) {
        ApiResponse<?> apiRespose = null;

        if (errors.hasErrors()) {
            log.info("Validation error");
            apiRespose = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(this.apiValidation.getErrorMessages(errors))
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(apiRespose);

        }

        try {
            log.info("Validation is valid");
            var address = this.addressService.findById(requests.getTargetId());
            if (!address.isPresent()) {
                log.info("Address with id: " + requests.getTargetId() + " not found");
                apiRespose = ApiResponse.builder()
                        .message(ApiMessage.FAILED)
                        .error("Address with id: " + requests.getTargetId() + " not found")
                        .data(null)
                        .build();
                return ResponseEntity.badRequest().body(apiRespose);
            }

            address = Optional.of(this.addressService.mapToEntity(requests.getData()));
            address.get().setId(requests.getTargetId());
            address = this.addressService.update(address.get());
            apiRespose = ApiResponse.builder()
                    .message(ApiMessage.SUCCESS)
                    .error(null)
                    .data(address)
                    .build();
            return ResponseEntity.ok(apiRespose);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            apiRespose = ApiResponse.builder()
                    .message(ApiMessage.ERROR)
                    .error(e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.internalServerError().body(apiRespose);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAddressByAuthorId(
            @RequestBody @Valid ApiTargetIdRequest request,
            Errors errors) {

        ApiResponse<?> apiResponse = null;

        if (errors.hasErrors()) {
            log.error("Validation error");
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(this.apiValidation.getErrorMessages(errors))
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);

        }

        try {
            log.info("Validation is valid");
            var author = this.authorService.findById(request.getTargetId());

            if (!author.isPresent()) {
                apiResponse = ApiResponse.builder()
                        .message(ApiMessage.FAILED)
                        .error("Author with id: " + request.getTargetId() + " not found")
                        .data(null)
                        .build();
                return ResponseEntity.badRequest().body(apiResponse);

            }

            var address = author.get().getAddress();
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.SUCCESS)
                    .error(null)
                    .data(address)
                    .build();
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.ERROR)
                    .error(e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.internalServerError().body(apiResponse);
        }
    }
}
