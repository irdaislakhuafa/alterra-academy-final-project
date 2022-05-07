package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BooksBorrowingDto;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BooksBorrowingService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/books/borrowing" })
@RequiredArgsConstructor
public class BooksBorrowingController {
    private final BooksBorrowingService booksBorrowingService;
    private final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            var all = this.booksBorrowingService.findAll();
            return ResponseEntity.ok(success(all));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid BooksBorrowingDto request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Error validation");
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var booksBorrowing = this.booksBorrowingService.mapToEntity(request);
            booksBorrowing = this.booksBorrowingService.save(booksBorrowing).get();
            return ResponseEntity.ok(success(booksBorrowing));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

}
