package com.irdaislakhuafa.alterraacademyfinalproject.controllers.categories;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = { "/category" })
@RequiredArgsConstructor
public class CategoryController {
    // private final
    @PostMapping
    public void name() {

    }
}
