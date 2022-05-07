package com.irdaislakhuafa.alterraacademyfinalproject.controllers.booksborrowing;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.booksborrowing.BooksBorrowingStudent;
import com.irdaislakhuafa.alterraacademyfinalproject.services.*;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/books/borrowing/student" })
@RequiredArgsConstructor
public class BooksBorrowingStudentController {
    private final BooksBorrowingService booksBorrowingService;
    private final ApiValidation apiValidation;
    private final BookService bookService;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody @Valid BooksBorrowingStudent request, Errors errors) {
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

            var student = this.studentService.findById(request.getStudentId());
            if (!student.isPresent()) {
                var message = "student with id: " + request.getStudentId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            booksBorrowing.get().getStudents().add(student.get());
            booksBorrowing = this.booksBorrowingService.update(booksBorrowing.get());
            return ResponseEntity.ok(success(booksBorrowing));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
