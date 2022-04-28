package com.irdaislakhuafa.alterraacademyfinalproject.controllers.categories;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.CategoryDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiRequestName;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.CategoryService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/categories" })
@RequiredArgsConstructor
public class CategoryController {
    private final ApiValidation apiValidation;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CategoryDto categoryDto, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            log.info("Validation is valid");
            var response = success(this.categoryService.save(this.categoryService.mapToEntity(categoryDto)));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            log.info("Request find all categories");
            var response = success(this.categoryService.findAll());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ApiChangeRequests<CategoryDto> requests, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var category = this.categoryService.findById(requests.getTargetId()).orElse(null);
            if (category == null) {
                var message = "category with id: " + requests.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var books = category.getBooks();
            category = this.categoryService.mapToEntity(requests.getData());
            category.setBooks(books);
            category = this.categoryService.update(category).get();

            return ResponseEntity.ok(success(category));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }
        try {
            var targetDelete = this.categoryService.findById(request.getTargetId());
            if (!targetDelete.isPresent()) {
                var message = "category with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }
            this.categoryService.deleteById(request.getTargetId());
            return ResponseEntity.ok(success(targetDelete));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = { "/findBy/id" })
    public ResponseEntity<?> findById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var category = this.categoryService.findById(request.getTargetId());
            var response = (category.isPresent()) ? success(category)
                    : failed("category with id: " + request.getTargetId() + " not found");

            return new ResponseEntity<>(response, (category.isPresent()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = { "/findBy/name" })
    public ResponseEntity<?> findByName(@RequestBody @Valid ApiRequestName requestName, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Validation error");
            return ResponseEntity.badRequest().body(failed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            log.info("Validation is valid");
            var category = this.categoryService.findByName(requestName.getName());
            if (!category.isPresent()) {
                var message = "category with name: " + requestName.getName() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            return ResponseEntity.ok(success(category));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
