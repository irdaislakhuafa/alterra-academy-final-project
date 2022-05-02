package com.irdaislakhuafa.alterraacademyfinalproject.controllers.students;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse.*;

import javax.validation.Valid;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.StudentDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiChangeRequests;
import com.irdaislakhuafa.alterraacademyfinalproject.model.requests.ApiTargetIdRequest;
import com.irdaislakhuafa.alterraacademyfinalproject.services.StudentService;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = { "/students" })
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final ApiValidation apiValidation;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            log.info("Requests find all students");
            var students = this.studentService.findAll();
            var responses = success(students);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid StudentDto studentDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            log.info("Validation is valid");
            var savedStudent = this.studentService.save(this.studentService.mapToEntity(studentDto));
            return ResponseEntity.ok(success(savedStudent));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ApiChangeRequests<StudentDto> requests, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(errors));
        }

        try {
            log.info("Validation is valid");
            var student = this.studentService.findById(requests.getTargetId());
            var targetUpdate = this.studentService.mapToEntity(requests.getData());

            targetUpdate.setId(student.get().getId());
            targetUpdate.setAddresses(student.get().getAddresses());

            var updatedStudent = this.studentService.update(targetUpdate);
            return ResponseEntity.ok(success(updatedStudent));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(validationFailed(this.apiValidation.getErrorMessages(errors)));
        }

        try {
            log.info("Validation is valid");
            var targetDeleted = this.studentService.findById(request.getTargetId());
            var message = "student with id: " + request.getTargetId() + " not found";

            if (!targetDeleted.isPresent()) {
                log.warn(message);
                return ResponseEntity.badRequest().body(failed(message));
            }

            this.studentService.deleteById(request.getTargetId());
            return ResponseEntity.ok(success(targetDeleted));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = { "/findBy/id" })
    public ResponseEntity<?> findById(@RequestBody @Valid ApiTargetIdRequest request, Errors errors) {
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

            return ResponseEntity.ok(success(student));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }
}
