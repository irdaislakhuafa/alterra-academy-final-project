package com.irdaislakhuafa.alterraacademyfinalproject.controllers.publisher;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.PublisherDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AddressService;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.PublisherService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiMessage;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = { "/publishers" })
public class PublisherController {
    private final PublisherService publisherService;
    private final ApiValidation apiValidation;
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PublisherDto publisherDto, Errors errors) {
        ApiResponse<?> apiResponse = null;

        if (errors.hasErrors()) {
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(this.apiValidation.getErrorMessages(errors))
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }

        try {
            var publisher = publisherService.mapToEntity(publisherDto);
            this.addressService.save(publisher.getAddress());
            var savedPublisher = publisherService.save(publisher);
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.SUCCESS)
                    .error(null)
                    .data(savedPublisher)
                    .build();
            return ResponseEntity.ok().body(apiResponse);

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

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Request find all publishers");
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;
        try {
            var authors = publisherService.findAll();
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.SUCCESS)
                    .error(null)
                    .data(authors)
                    .build();
            responses = ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.ERROR)
                    .error(e.getMessage())
                    .data(null)
                    .build();
            responses = ResponseEntity.internalServerError().body(apiResponse);
        }

        return responses;
    }
}