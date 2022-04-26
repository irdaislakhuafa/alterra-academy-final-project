package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AddressService;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AuthorService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiMessage;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse;

import org.hibernate.exception.ConstraintViolationException;
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
@RequestMapping(value = { "/authors" })
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AddressService addressService;

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
            List<String> errorMessages = new ArrayList<>();

            for (var error : errors.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(errorMessages)
                    .build();
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            try {
                var savedAddress = addressService.saveAll(addressService.mapToEntities(authorDto.getAddress()));
                var author = authorService.mapToEntity(authorDto);
                author.setAddress(savedAddress);

                var savedAuthor = authorService.save(author);
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
}
