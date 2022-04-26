package com.irdaislakhuafa.alterraacademyfinalproject.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ApiValidation {
    public List<String> getErrorMessages(Errors errors) {
        return errors.getFieldErrors().stream().map((error) -> error.getDefaultMessage()).collect(Collectors.toList());
    }
}
