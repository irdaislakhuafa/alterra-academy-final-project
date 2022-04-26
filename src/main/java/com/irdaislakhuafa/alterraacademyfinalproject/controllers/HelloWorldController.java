package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiMessage;
import com.irdaislakhuafa.alterraacademyfinalproject.utils.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloWorldController {

    @GetMapping(value = { "/hello-world" })
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message(ApiMessage.SUCCESS)
                        .error(null)
                        .data("Hello World")
                        .build());
    }
}
