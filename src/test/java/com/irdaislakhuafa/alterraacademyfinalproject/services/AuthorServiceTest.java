package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.*;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AuthorRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest(classes = { AuthorService.class })
public class AuthorServiceTest implements BaseServiceTest {
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    private final Address address = Address.builder()
            .id("addressId")
            .city("tuban")
            .country("indonesia")
            .build();

    private final Category category = Category.builder()
            .id("categoryId")
            .name("category 1")
            .description("-")
            .build();

    private final Book book = Book.builder()
            .id("bookId")
            .title("book 1")
            .description("-")
            .categories(List.of(category))
            .build();

    private final Author author = Author.builder()
            .id("id")
            .firstName("irda")
            .lastName("islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .address(List.of(address))
            .books(List.of(book))
            .build();

    private final AuthorDto authorDto = AuthorDto.builder()
            .firstName("irda")
            .lastName("islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.authorRepository.save(author)).thenReturn(author);

        var result = this.authorService.save(author);

        assertNotNull(result);
        assertEquals(author.getFirstName(), result.get().getFirstName());
    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.authorRepository.findById("id")).thenReturn(Optional.of(author));
        var result = this.authorService.findById("id");

        assertNotNull(result);
        assertEquals(author.getEmail(), result.get().getEmail());
    }

    @Test
    @Override
    public void testFindByIdFailed() {
        when(this.authorRepository.findById("id")).thenReturn(Optional.empty());
        var result = this.authorService.findById("id");

        assertNotNull(result);
        assertTrue(!result.isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.authorRepository.existsById("id")).thenReturn(true);
        var result = this.authorService.deleteById("id");
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.authorRepository.existsById("id")).thenReturn(false);
        var result = this.authorService.deleteById("id");
        assertFalse(result);
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.authorRepository.findAll()).thenReturn(List.of(author));
        var result = this.authorService.findAll();
        assertNotNull(result);

        assertEquals(author.getEmail(), result.get(0).getEmail());
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        var result = this.authorService.mapToEntity(authorDto);
        assertNotNull(result);

        assertEquals(author.getEmail(), result.getEmail());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {
        assertThrows(NullPointerException.class, () -> this.authorService.mapToEntity(null));
    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var result = this.authorService.mapToEntities(List.of(authorDto));
        assertNotNull(result);

        assertEquals(author.getEmail(), result.get(0).getEmail());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {
        assertThrows(NullPointerException.class, () -> this.authorService.mapToEntities(null));
    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.authorRepository.saveAll(anyList())).thenReturn(List.of(author));
        var result = this.authorService.saveAll(List.of(author));

        assertNotNull(result);
        assertEquals(author.getEmail(), result.get(0).getEmail());
    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.authorRepository.findAllById(anyList())).thenReturn(List.of(author));
        var result = this.authorService.findAllById(List.of("id"));
        assertNotNull(result);
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.authorRepository.save(any(Author.class))).thenReturn(author);
        var result = this.authorService.update(author);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {
        when(this.authorRepository.save(any())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> this.authorService.save(author));
    }

    @Test
    @Override
    public void testUpdateFailed() {

    }

    @Override
    public void testSaveAllFailed() {

    }

}
