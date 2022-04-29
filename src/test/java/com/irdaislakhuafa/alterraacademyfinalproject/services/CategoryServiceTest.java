package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Category;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.CategoryRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest
public class CategoryServiceTest implements BaseServiceTest {

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

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.categoryRepository.save(any())).thenReturn(category);
        var result = this.categoryService.save(category);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(result.get(), category);
    }

    @Test
    @Override
    public void testSaveFailed() {
        when(this.categoryRepository.save(any())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> this.categoryService.save(category));
    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.categoryRepository.findById("id")).thenReturn(Optional.of(category));
        var result = this.categoryService.findById("id");
        assertTrue(result.isPresent());

    }

    @Test
    @Override
    public void testFindByIdFailed() {
        when(this.categoryRepository.findById("id")).thenReturn(Optional.empty());
        var resultFailed = this.categoryService.findById("idFailed");
        assertFalse(resultFailed.isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.categoryRepository.existsById("id")).thenReturn(true);
        var result = this.categoryService.deleteById("id");

        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {

    }

    @Test
    @Override
    public void testFindAllSuccess() {

    }

    @Test
    @Override
    public void testMapToEntitySuccess() {

    }

    @Test
    @Override
    public void testMapToEntityFailed() {

    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {

    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {

    }

    @Test
    @Override
    public void testSaveAllSuccess() {

    }

    @Test
    @Override
    public void testSaveAllFailed() {

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {

    }

    @Test
    @Override
    public void testUpdateSuccess() {

    }

    @Test
    @Override
    public void testUpdateFailed() {

    }
}
