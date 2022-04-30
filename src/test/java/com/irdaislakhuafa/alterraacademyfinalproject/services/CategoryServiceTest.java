package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.*;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.CategoryDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Category;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.CategoryRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@Tag(value = "categoryServiceTest")
@SpringBootTest
public class CategoryServiceTest implements BaseServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    private final Category category = Category.builder()
            .id("id")
            .name("category 1")
            .description("-")
            .books(new ArrayList<>())
            .build();

    private final CategoryDto categoryDto = CategoryDto.builder()
            .name("category 1")
            .description("-")
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
        when(this.categoryRepository.existsById("id")).thenReturn(false);
        var result = this.categoryService.deleteById("id");

        assertFalse(result);
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.categoryRepository.findAll()).thenReturn(List.of(category));
        var result = this.categoryService.findAll();

        assertNotNull(result);
        assertEquals(List.of(category), result);
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        var mappedCategory = this.categoryService.mapToEntity(categoryDto);
        assertNotNull(mappedCategory);
        assertEquals(category.getName(), mappedCategory.getName());
        assertEquals(category.getDescription(), mappedCategory.getDescription());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {
        assertThrows(NullPointerException.class, () -> this.categoryService.mapToEntity(null));
    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var result = this.categoryService.mapToEntities(List.of(categoryDto));
        assertNotNull(result);
        assertEquals(category.getClass(), result.get(0).getClass());
        assertEquals(category.getName(), result.get(0).getName());
        assertEquals(category.getDescription(), result.get(0).getDescription());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {
        assertThrows(NullPointerException.class, () -> this.categoryService.mapToEntities(null));
    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.categoryRepository.saveAll(anyList())).thenReturn(List.of(category));
        var result = this.categoryService.saveAll(List.of(category));
        assertNotNull(result);
        assertEquals(List.of(category), result);
    }

    @Test
    @Override
    public void testSaveAllFailed() {
        when(this.categoryRepository.saveAll(anyList())).thenReturn(null);
        var result = this.categoryService.saveAll(List.of(category));
        assertNull(result);
    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.categoryRepository.findAllById(List.of("id"))).thenReturn(List.of(category));
        var result = this.categoryService.findAllById(List.of("id"));
        assertNotNull(result);
        assertEquals(List.of(category), result);
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.categoryRepository.save(any())).thenReturn(category);
        var result = this.categoryService.update(category);
        assertNotNull(result);
        assertEquals(Optional.of(category), result);
    }

    @Test
    @Override
    public void testUpdateFailed() {
        when(this.categoryRepository.save(any())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> this.categoryService.update(category));
    }

    @Test
    public void testFindByName() {
        when(this.categoryRepository.findByNameEqualsIgnoreCase("name")).thenReturn(Optional.of(category));
        var result = this.categoryService.findByName("testWrongName");
        assertEquals(Optional.empty(), result);

        result = this.categoryService.findByName("name");
        assertTrue(result.isPresent());
    }
}
