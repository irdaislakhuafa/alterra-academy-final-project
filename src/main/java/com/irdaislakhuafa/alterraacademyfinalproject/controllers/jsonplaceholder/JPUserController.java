package com.irdaislakhuafa.alterraacademyfinalproject.controllers.jsonplaceholder;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import java.util.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder.requests.FindByEmailRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder.requests.Register;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;
import com.irdaislakhuafa.alterraacademyfinalproject.services.RoleService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.consume.JsonPlaceholderUserService;
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
    private final RoleService roleService;
    private final UserService userService;

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

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@RequestBody @Valid Register register, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var userJson = this.jpUserService.findByEmail(register.getEmail());
            var user = User.builder()
                    .name(userJson.get().getName())
                    .email(userJson.get().getEmail())
                    .password(userJson.get().getUsername())
                    .isEnable(true)
                    .roles(new HashSet<>(roleService.findByNames("user")))
                    .build();

            log.info("saving new user");
            var savedUser = userService.save(user);
            if (!savedUser.isPresent()) {
                return ResponseEntity.badRequest().body(failed("failed to save new user, please call IT service"));
            }

            var response = new HashMap<>() {
                {
                    put("message", "your password is username from json placeholder");
                    put("saved", savedUser);
                }
            };
            return ResponseEntity.ok(success(response));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(failed(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
