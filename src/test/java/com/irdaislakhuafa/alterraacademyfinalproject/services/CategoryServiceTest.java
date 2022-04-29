package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.ArrayList;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Category;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.CategoryRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    private final Category category = Category.builder()
            .id("id")
            .name("categoriy 1")
            .description("-")
            .books(new ArrayList<>())
            .build();
}
