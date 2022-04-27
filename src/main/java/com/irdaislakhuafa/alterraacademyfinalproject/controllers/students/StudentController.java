package com.irdaislakhuafa.alterraacademyfinalproject.controllers.students;

import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiValidation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = { "/students" })
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final ApiValidation apiValidation;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            log.info("Requests find all students");

        } catch (Exception e) {
        }
        return null;
    }
}
