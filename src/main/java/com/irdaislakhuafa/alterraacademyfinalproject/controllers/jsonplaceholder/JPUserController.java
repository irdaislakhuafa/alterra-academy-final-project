package com.irdaislakhuafa.alterraacademyfinalproject.controllers.jsonplaceholder;

import com.irdaislakhuafa.alterraacademyfinalproject.services.consume.JsonPlaceholderUserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = { "/api/v1/jsonplaceholder/users" })
public class JPUserController {
    private final JsonPlaceholderUserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
