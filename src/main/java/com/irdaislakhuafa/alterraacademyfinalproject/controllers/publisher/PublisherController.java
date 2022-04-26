package com.irdaislakhuafa.alterraacademyfinalproject.controllers.publisher;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = { "/publisher" })
public class PublisherController {
    @GetMapping
    public ResponseEntity<?> save(@RequestParam String param) {
        return null;
    }

}