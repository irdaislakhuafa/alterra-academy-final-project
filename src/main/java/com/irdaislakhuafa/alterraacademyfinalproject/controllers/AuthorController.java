package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Author;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AddressService;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.AuthorService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiMessage;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
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
            @RequestBody @Valid ApiChangeRequests<AuthorDto> updateRequest,
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

    @DeleteMapping
    public ResponseEntity<?> deleteById(
            @RequestBody @Valid ApiTargetIdRequest deleteRequests,
            Errors errors) {

        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;

        log.info("Request delete author by id");
        if (errors.hasErrors()) {
            log.error("Error validation");
            apiResponse = ApiResponse.builder()
                    .message(ApiMessage.FAILED)
                    .error(apiValidation.getErrorMessages(errors))
                    .data(null)
                    .build();
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            log.info("Validation is valid");
            try {
                var targetDeleted = authorService.findById(deleteRequests.getTargetId());
                if (!targetDeleted.isPresent()) {
                    log.debug("Author with id: " + deleteRequests.getTargetId() + " not found");
                    apiResponse = ApiResponse.builder()
                            .message(ApiMessage.FAILED)
                            .error("author with id: " + deleteRequests.getTargetId() + " not found")
                            .data(null)
                            .build();
                    responses = ResponseEntity.badRequest().body(apiResponse);

                } else {
                    authorService.deleteById(deleteRequests.getTargetId());
                    apiResponse = ApiResponse.builder()
                            .message(ApiMessage.SUCCESS)
                            .error(null)
                            .data(targetDeleted.get())
                            .build();
                    responses = ResponseEntity.ok().body(apiResponse);
                    log.info("Success deleted author id: " + targetDeleted.get().getId());

                    System.out.println("\033\143");
                    System.out.println(targetDeleted.get().getAddress());
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

    @GetMapping(value = { "/findBy/id" })
    public ResponseEntity<?> findById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
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
            log.error("Validation is valid");
            try {
                var author = authorService.findById(request.getTargetId());
                var isPresent = author.isPresent();

                apiResponse = ApiResponse.builder()
                        .message((isPresent) ? ApiMessage.SUCCESS : ApiMessage.FAILED)
                        .data((isPresent) ? author.get() : null)
                        .error((isPresent) ? null : "author with id: " + request.getTargetId() + " not found")
                        .build();
                responses = new ResponseEntity<>(apiResponse, (isPresent) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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

    @PostMapping(value = { "/address" })
    public ResponseEntity<?> addAddress(
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

    @PutMapping(value = { "/address" })
    public ResponseEntity<?> updateAddress(@RequestBody @Valid ApiChangeRequests<AddressDto> requests, Errors errors) {
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
}
