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
            if (!bookService.existsById(bookAuthor.getBookId())) {
                var message = "book with id: " + bookAuthor.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            if (!authorService.existsById(bookAuthor.getAuthorId())) {
                var message = "author with id: " + bookAuthor.getAuthorId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var book = this.bookService.findById(bookAuthor.getBookId()).get();
            book.getAuthors().add(this.authorService.findById(bookAuthor.getAuthorId()).get());
            book = bookService.update(book).get();
            return ResponseEntity.ok(success(book));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
