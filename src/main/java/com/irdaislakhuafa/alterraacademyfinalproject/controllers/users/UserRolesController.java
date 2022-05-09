package com.irdaislakhuafa.alterraacademyfinalproject.controllers.users;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/api/v1/users/roles" })
@RequiredArgsConstructor
public class UserRolesController {
    private final UserService userService;
    private final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> getRoles(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var user = this.userService.findById(request.getTargetId());
            if (user.isPresent()) {
                var message = "user with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            return ResponseEntity.ok(success(user.get().getRoles()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
