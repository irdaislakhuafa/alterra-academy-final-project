package com.irdaislakhuafa.alterraacademyfinalproject.controllers.users;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.UserDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.users.UserAuthRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/api/v1/users" })
@RequiredArgsConstructor
public class UserController {
    private final ApiValidation apiValidation;
    private final UserService userService;

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@RequestBody @Valid UserDto request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var userIsExist = this.userService.findByEmail(request.getEmail()).isPresent();
            if (userIsExist) {
                var message = "user with email: " + request.getEmail() + " already exists";
                return ResponseEntity.badRequest().body(failed(message));
            }

            var user = this.userService.mapToEntity(request);
            user = this.userService.save(user).get();
            return ResponseEntity.ok(success(user));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PostMapping(value = { "/auth" })
    public ResponseEntity<?> auth(@RequestBody @Valid UserAuthRequest request, Errors errors) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            var users = this.userService.findAll();
            return ResponseEntity.ok(success(users));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            this.userService.deleteById(request.getTargetId());
            return ResponseEntity.ok(success(null));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(failed(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

}
