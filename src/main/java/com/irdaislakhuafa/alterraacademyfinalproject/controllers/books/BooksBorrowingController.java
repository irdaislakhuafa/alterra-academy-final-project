package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import com.irdaislakhuafa.alterraacademyfinalproject.services.BooksBorrowingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/books/borrowing" })
@RequiredArgsConstructor
public class BooksBorrowingController {
    private final BooksBorrowingService booksBorrowingService;

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
}
