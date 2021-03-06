package com.irdaislakhuafa.alterraacademyfinalproject.controllers.roles;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.RoleDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.RoleService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/api/v1/roles" })
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            var roles = this.roleService.findAll();
            return ResponseEntity.ok(success(roles));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid RoleDto request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var role = this.roleService.mapToEntity(request);
            role = this.roleService.save(role).get();
            return ResponseEntity.ok(role);
        } catch (DataIntegrityViolationException e) {
            var message = "role with name: " + request.getName() + " alredy exists";
            return ResponseEntity.badRequest().body(failed(message));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ApiChangeRequests<RoleDto> request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var role = this.roleService.mapToEntity(request.getData());
            role.setId(request.getTargetId());
            role = this.roleService.update(role).get();
            return ResponseEntity.ok(success(role));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.ok(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            this.roleService.deleteById(request.getTargetId());
            return ResponseEntity.ok(success(null));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(failed(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = { "/findBy/id" })
    public ResponseEntity<?> findById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(apiValidation.getErrorMessages(errors)));
        }

        try {
            var role = this.roleService.findById(request.getTargetId());
            var messageNotFound = "role with id: " + request.getTargetId() + " not found";
            return (role.isPresent()) ? ResponseEntity.ok(success(role))
                    : ResponseEntity.badRequest().body(failed(messageNotFound));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
