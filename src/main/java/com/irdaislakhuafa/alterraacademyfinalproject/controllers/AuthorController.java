package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import java.util.Optional;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Author;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.UpdateRequest;
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
@RestController
@RequestMapping(value = { "/authors" })
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AddressService addressService;
    public final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Request find all authors");
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;
        try {
            var authors = authorService.findAll();
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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AuthorDto authorDto, Errors errors) {
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;

        log.info("Request save new author");

        if (errors.hasErrors()) {
            log.error("Error validation");
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(apiValidation.getErrorMessages(errors))
                    .build();
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            try {
                var savedAuthor = authorService.save(authorService.mapToEntity(authorDto));
                apiResponse = ApiResponse.builder()
                        .message(ApiMessage.SUCCESS)
                        .error(null)
                        .data(savedAuthor)
                        .build();
                responses = ResponseEntity.ok().body(apiResponse);

            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.builder()
                        .message(ApiMessage.ERROR)
                        .error(e.getMessage())
                        .data(null)
                        .build();
                responses = ResponseEntity.internalServerError().body(apiResponse);
            }
        }

        return responses;
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody @Valid UpdateRequest<AuthorDto> updateRequest,
            Errors errors) {

        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;

        if (errors.hasErrors()) {
            log.error("Error validation");
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(apiValidation.getErrorMessages(errors))
                    .data(null)
                    .build();
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            try {
                var author = authorService.findById(updateRequest.getTargetId());
                if (!author.isPresent()) {
                    apiResponse = ApiResponse.builder()
                            .message(ApiMessage.FAILED)
                            .error("data with id: " + updateRequest.getTargetId() + " not found")
                            .data(null)
                            .build();
                    responses = ResponseEntity.badRequest().body(apiResponse);

                } else {
                    var address = author.get().getAddress();
                    author = Optional.of(
                            Author.builder()
                                    .id(author.get().getId())
                                    .firstName(updateRequest.getData().getFirstName().trim())
                                    .lastName(updateRequest.getData().getLastName().trim())
                                    .email(updateRequest.getData().getEmail().trim())
                                    .address(address)
                                    .build());

                    var updatedAuthor = authorService.update(author.get());
                    apiResponse = ApiResponse.builder()
                            .message(ApiMessage.SUCCESS)
                            .error(null)
                            .data(updatedAuthor)
                            .build();
                    responses = ResponseEntity.ok().body(apiResponse);
                }
            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.builder()
                        .message(ApiMessage.ERROR)
                        .error(e.getMessage())
                        .data(null)
                        .build();
                responses = ResponseEntity.internalServerError().body(apiResponse);
            }
        }

        return responses;
    }
}
