package com.irdaislakhuafa.alterraacademyfinalproject.controllers.authors;

import java.util.Optional;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Author;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.AuthorService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/api/v1/authors" })
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    public final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Request find all authors");
        ResponseEntity<?> responses = null;
        ApiResponse<?> apiResponse = null;
        try {
            var authors = authorService.findAll();
            apiResponse = ApiResponse.success(authors);
            responses = ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            apiResponse = ApiResponse.error(e.getMessage());
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
            apiResponse = ApiResponse.validationFailed(this.apiValidation.getErrorMessages(errors));
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            try {
                var savedAuthor = authorService.save(authorService.mapToEntity(authorDto));
                apiResponse = ApiResponse.success(savedAuthor);
                responses = ResponseEntity.ok().body(apiResponse);

            } catch (Exception e) {
                log.error("Error: " + e.getMessage());
                apiResponse = ApiResponse.error(e.getMessage());
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
            apiResponse = ApiResponse.validationFailed(this.apiValidation.getErrorMessages(errors));
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            try {
                var author = authorService.findById(updateRequest.getTargetId());
                if (!author.isPresent()) {
                    apiResponse = ApiResponse.failed("data with id: " + updateRequest.getTargetId() + " not found");
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
                    apiResponse = ApiResponse.success(updatedAuthor);
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

        log.info("Request delete author by id");
        if (errors.hasErrors()) {
            apiResponse = ApiResponse.validationFailed(this.apiValidation.getErrorMessages(errors));
            responses = ResponseEntity.badRequest().body(apiResponse);

        } else {
            log.info("Validation is valid");
            try {
                var targetDeleted = authorService.findById(deleteRequests.getTargetId());
                if (!targetDeleted.isPresent()) {
                    log.debug("Author with id: " + deleteRequests.getTargetId() + " not found");
                    apiResponse = ApiResponse.failed("author with id: " + deleteRequests.getTargetId() + " not found");
                    responses = ResponseEntity.badRequest().body(apiResponse);

                } else {
                    authorService.deleteById(deleteRequests.getTargetId());
                    apiResponse = ApiResponse.success(targetDeleted);
                    responses = ResponseEntity.ok().body(apiResponse);
                    log.info("Success deleted author id: " + targetDeleted.get().getId());
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
            apiResponse = ApiResponse.validationFailed(this.apiValidation.getErrorMessages(errors));
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
                apiResponse = ApiResponse.error(e.getMessage());
                responses = ResponseEntity.internalServerError().body(apiResponse);
            }
        }

        return responses;
    }
}
