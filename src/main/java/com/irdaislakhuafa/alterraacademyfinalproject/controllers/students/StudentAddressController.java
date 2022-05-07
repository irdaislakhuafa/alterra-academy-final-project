package com.irdaislakhuafa.alterraacademyfinalproject.controllers.students;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import java.util.List;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.AddressService;
import com.irdaislakhuafa.alterraacademyfinalproject.services.StudentService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = { "/api/v1/students/address" })
@RequiredArgsConstructor
@Slf4j
public class StudentAddressController {
    private final AddressService addressService;
    private final ApiValidation apiValidation;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid ApiChangeRequests<List<AddressDto>> requests, Errors errors) {
        log.info("Request add address for author");

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var targetUpdate = this.studentService.findById(requests.getTargetId());

            if (!targetUpdate.isPresent()) {
                var message = "student with id: " + requests.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            targetUpdate = this.studentService.addAddress(targetUpdate, requests.getData());
            return ResponseEntity.ok(targetUpdate);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ApiChangeRequests<AddressDto> requests, Errors errors) {
        log.info("Request add address for author");
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            if (!this.addressService.existsById(requests.getTargetId())) {
                var message = "address with id: " + requests.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            var targetUpdate = this.addressService.mapToEntity(requests.getData());
            targetUpdate.setId(requests.getTargetId());
            targetUpdate = this.addressService.update(targetUpdate).get();
            return ResponseEntity.ok(success(targetUpdate));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAddressByStudentId(
            @RequestBody @Valid ApiTargetIdRequest request,
            Errors errors) {

        log.info("Request find address by student id");
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var student = this.studentService.findById(request.getTargetId());
            if (!student.isPresent()) {
                var message = "student with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            return ResponseEntity.ok(success(student.get().getAddresses()));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        log.info("Request delete address student by id");
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            var targetDelete = this.addressService.findById(request.getTargetId());
            if (!targetDelete.isPresent()) {
                var message = "address with id: " + request.getTargetId() + " not found";
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            this.addressService.deleteById(request.getTargetId());
            return ResponseEntity.ok(success(targetDelete));

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
