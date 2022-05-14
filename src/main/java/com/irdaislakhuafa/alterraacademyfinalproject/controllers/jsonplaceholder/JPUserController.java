package com.irdaislakhuafa.alterraacademyfinalproject.controllers.jsonplaceholder;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder.requests.FindByEmailRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.consume.JsonPlaceholderUserService;
import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import java.util.NoSuchElementException;

import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = { "/api/v1/jsonplaceholder/users" })
public class JPUserController {
    private final JsonPlaceholderUserService jpUserService;
    private final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(success(jpUserService.findAll()));
    }

    @GetMapping(value = { "/findBy/email" })
    public ResponseEntity<?> findByEmail(@RequestBody @Valid FindByEmailRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var user = jpUserService.findByEmail(request.getEmail());
            return ResponseEntity.ok(success(user));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(failed(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
