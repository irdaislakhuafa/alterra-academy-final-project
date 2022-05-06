package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books.BookAuthor;
import com.irdaislakhuafa.alterraacademyfinalproject.services.AuthorService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BookService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/books/authors" })
@RequiredArgsConstructor
public class BookAuthorController {
    private final ApiValidation apiValidation;
    private final AuthorService authorService;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid BookAuthor bookAuthor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var book = this.bookService.findById(bookAuthor.getBookId());
            if (!book.isPresent()) {
                var message = "book with id: " + bookAuthor.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var author = this.authorService.findById(bookAuthor.getAuthorId());
            if (!author.isPresent()) {
                var message = "author with id: " + bookAuthor.getAuthorId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            book.get().getAuthors().add(author.get());
            book = bookService.update(book.get());

            return ResponseEntity.ok(success(book));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid BookAuthor requests, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var book = this.bookService.findById(requests.getBookId());

            if (!book.isPresent()) {
                var message = "book with id: " + requests.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var authorIsPresentOnBook = book
                    .get()
                    .getAuthors()
                    .stream()
                    .filter((a) -> a.getId().equals(requests.getAuthorId()))
                    .findFirst()
                    .isPresent();

            if (!authorIsPresentOnBook) {
                var message = "author with id: " + requests.getAuthorId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            book.get().getAuthors().removeIf((a) -> a.getId().equals(requests.getAuthorId()));
            book = this.bookService.update(book.get());
            return ResponseEntity.ok(success(null));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
