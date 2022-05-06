package com.irdaislakhuafa.alterraacademyfinalproject.controllers.books;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books.BookCategory;
import com.irdaislakhuafa.alterraacademyfinalproject.services.BookService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.CategoryService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/books/categories" })
@RequiredArgsConstructor
public class BookCategoryController {
    private final ApiValidation apiValidation;
    private final BookService bookService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid BookCategory bookCategory, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            if (!bookService.existsById(bookCategory.getBookId())) {
                var message = "book with id: " + bookCategory.getBookId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var category = this.categoryService.findById(bookCategory.getCategoryId());
            if (!category.isPresent()) {
                var message = "category with id: " + bookCategory.getCategoryId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var book = this.bookService.findById(bookCategory.getBookId());
            book.get().getCategories().add(category.get());
            book = bookService.update(book.get());
            return ResponseEntity.ok(success(book));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

}
