package com.irdaislakhuafa.alterraacademyfinalproject.controllers.booksborrowing;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.booksborrowing.BooksBorrowingBook;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BookService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BooksBorrowingService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/api/v1/books/borrowing/book" })
@RequiredArgsConstructor
public class BooksBorrowingBookController {
    private final BooksBorrowingService booksBorrowingService;
    private final ApiValidation apiValidation;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid BooksBorrowingBook request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation failed");
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var booksBorrowing = this.booksBorrowingService.findById(request.getBooksBorrowingId());
            if (!booksBorrowing.isPresent()) {
                var message = "books_borrowing with id: " + request.getBooksBorrowingId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var book = this.bookService.findById(request.getBookId());
            if (!book.isPresent()) {
                var message = "book with id: " + request.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            booksBorrowing.get().getBooks().add(book.get());
            booksBorrowing = this.booksBorrowingService.update(booksBorrowing.get());
            return ResponseEntity.ok(success(booksBorrowing));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody @Valid BooksBorrowingBook request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation failed");
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var booksBorrowing = this.booksBorrowingService.findById(request.getBooksBorrowingId());
            if (!booksBorrowing.isPresent()) {
                var message = "books_borrowing with id: " + request.getBooksBorrowingId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var book = this.bookService.findById(request.getBookId());
            if (!book.isPresent()) {
                var message = "book with id: " + request.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            booksBorrowing.get().getBooks().removeIf((v) -> v.getId().equals(request.getBookId()));
            booksBorrowing = this.booksBorrowingService.update(booksBorrowing.get());
            return ResponseEntity.ok(success(booksBorrowing));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
