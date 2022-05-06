package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books.BookPublisher;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BookService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.PublisherService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/books/publishers" })
@RequiredArgsConstructor
public class BookPublisherController {

    private final ApiValidation apiValidation;
    private final BookService bookService;
    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid BookPublisher bookPublisher, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            if (!bookService.existsById(bookPublisher.getBookId())) {
                var message = "book with id: " + bookPublisher.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var publisher = this.publisherService.findById(bookPublisher.getPublisherId());
            if (!publisher.isPresent()) {
                var message = "publisher with id: " + bookPublisher.getPublisherId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var book = this.bookService.findById(bookPublisher.getBookId());
            book.get().getPublishers().add(publisher.get());
            book = bookService.update(book.get());
            return ResponseEntity.ok(success(book));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
