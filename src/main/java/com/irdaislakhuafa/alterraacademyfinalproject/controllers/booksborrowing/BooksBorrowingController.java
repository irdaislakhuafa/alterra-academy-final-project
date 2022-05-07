package com.irdaislakhuafa.alterraacademyfinalproject.controllers.booksborrowing;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BooksBorrowingDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.BooksBorrowing;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.booksborrowing.BooksBorrowingBook;
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
@RequestMapping(value = { "/api/v1/books/borrowing" })
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
    public ResponseEntity<?> save(@RequestBody @Valid BooksBorrowingDto request, Errors errors) {
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

    @PutMapping(value = { "/returned" })
    public ResponseEntity<?> returned(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation failed");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }
        try {
            var value = this.booksBorrowingService.findById(request.getTargetId());
            if (!value.isPresent()) {
                var message = "books_borrowing with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            value.get().setStatus(BorrowStatus.RETURNED);
            value = this.booksBorrowingService.update(value.get());
            return ResponseEntity.ok(success(value));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PutMapping(value = { "/borrowed" })
    public ResponseEntity<?> borrowed(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation failed");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }
        try {
            var value = this.booksBorrowingService.findById(request.getTargetId());
            if (!value.isPresent()) {
                var message = "books_borrowing with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            value.get().setStatus(BorrowStatus.BORROWED);
            value = this.booksBorrowingService.update(value.get());
            return ResponseEntity.ok(success(value));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PostMapping(value = { "/add/book" })
    public ResponseEntity<?> addBook(@RequestBody @Valid BooksBorrowingBook request, Errors errors) {
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

    @PostMapping(value = { "/add/student" })
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
