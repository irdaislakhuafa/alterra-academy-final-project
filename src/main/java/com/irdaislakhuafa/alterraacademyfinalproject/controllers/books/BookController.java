package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.error;
import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.success;
import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.validationFailed;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BookDto;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BookService;
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
@RequestMapping(value = { "/books" })
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
    public ResponseEntity<?> findAll() {
        try {
            log.info("Request find all books");
            var response = success(this.bookService.findAll());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

}
