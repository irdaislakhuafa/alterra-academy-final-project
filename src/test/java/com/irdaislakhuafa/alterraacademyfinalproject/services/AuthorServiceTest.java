package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.*;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AuthorRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest(classes = { AuthorService.class })
public class AuthorServiceTest {
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

    @Test
    public void testSaveSuccess() {
        when(this.authorRepository.save(author)).thenReturn(author);

        var result = this.authorService.save(author);

        assertNotNull(result);
        assertEquals(author.getFirstName(), result.get().getFirstName());
    }

    @Test
    public void testFindByIdSuccess() {
        when(this.authorRepository.findById("id")).thenReturn(Optional.of(author));
        var result = this.authorService.findById("id");

        assertNotNull(result);
    }
}
