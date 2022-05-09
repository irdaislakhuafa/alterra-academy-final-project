package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BookDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BookService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/api/v1/books" })
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ApiValidation apiValidation;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid BookDto bookDto, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            log.info("Validation is valid");
            var response = success(this.bookService.save(this.bookService.mapToEntity(bookDto)));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "showDeleted", defaultValue = "false") boolean showDeleted) {
        try {
            log.info("Request find all books");
            var response = success(this.bookService.findAll(showDeleted));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ApiChangeRequests<BookDto> requests, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var book = this.bookService.mapToEntity(requests.getData());
            book.setId(requests.getTargetId());
            book = this.bookService.update(book).get();

            return ResponseEntity.ok(success(book));
        } catch (NoSuchElementException e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(failed(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        log.info("Request delete book by id");
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var targetDelete = this.bookService.findById(request.getTargetId());
            var message = "Book with id: " + request.getTargetId() + " not found";

            if (!targetDelete.isPresent()) {
                log.error(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            return ResponseEntity.ok(success(this.bookService.deleteById(request.getTargetId())));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(value = { "/findBy/id" })
    public ResponseEntity<?> findById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var book = this.bookService.findById(request.getTargetId());
            if (!book.isPresent()) {
                var message = "book with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            return ResponseEntity.ok(success(book));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
