package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BooksBorrowingDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.BooksBorrowing;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;
import com.irdaislakhuafa.alterraacademyfinalproject.services.*;
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
    private final BookService bookService;
    private final StudentService studentService;

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
            var books = this.bookService.findAllById(request.getBooksIds());
            if (books.size() <= 0) {
                var message = "book with id: " + request.getBooksIds() + " not found";
                return ResponseEntity.badRequest().body(failed(message));
            }

            var students = this.studentService.findAllById(request.getStudentsIds());
            if (students.size() <= 0) {
                var message = "student with id: " + request.getStudentsIds() + " not found";
                return ResponseEntity.badRequest().body(failed(message));
            }

            var booksBorrowing = BooksBorrowing.builder()
                    .books(books)
                    .students(students)
                    .status(BorrowStatus.valueOf(request.getStatus().toUpperCase()))
                    .build();

            booksBorrowing = this.booksBorrowingService.save(booksBorrowing).get();
            return ResponseEntity.ok(success(booksBorrowing));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

}
