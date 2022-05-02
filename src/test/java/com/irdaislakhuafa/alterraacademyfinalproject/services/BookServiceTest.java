package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BookDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Author;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Book;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Category;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.BookRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@Tag(value = "bookServiceTest")
@SpringBootTest

public class BookServiceTest implements BaseServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private PublisherService publisherService;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    private final Address address = Address.builder()
            .id("addressId")
            .city("tuban")
            .country("indonesia")
            .build();

    private final Author author = Author.builder()
            .id("authorId")
            .firstName("irda")
            .lastName("islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .address(List.of(address))
            .build();

    private final Category category = Category.builder()
            .id("categoryId")
            .name("category 1")
            .description("-")
            .build();

    private final Publisher publisher = Publisher.builder()
            .name("publisher 1")
            .email(author.getEmail())
            .address(address)
            .build();

    private final Book book = Book.builder()
            .id("bookId")
            .title("Book 1")
            .description("-")
            .authors(List.of(author))
            .publishedDate(new Date())
            .categories(List.of(category))
            .publishers(null)
            .build();

    private final BookDto bookDto = BookDto.builder()
            .title("book 1")
            .description("-")
            .authorIds(List.of(author.getId()))
            .publisherIds(List.of(publisher.getId()))
            .categoryIds(List.of(category.getId()))
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.bookRepository.save(any())).thenReturn(book);
        var result = this.bookService.save(book);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {

    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.bookRepository.findById("bookId")).thenReturn(Optional.of(book));
        var result = this.bookService.findById("bookId");
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testFindByIdFailed() {
        when(this.bookRepository.findById("bookId")).thenReturn(Optional.of(book));
        var result = this.bookService.findById("bookIdFailed");
        assertFalse(result.isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.bookRepository.existsById("bookId")).thenReturn(true);
        var result = this.bookService.deleteById("bookId");
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.bookRepository.existsById("bookId")).thenReturn(true);
        var result = this.bookService.deleteById("bookIdFailed");
        assertFalse(result);

    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.bookRepository.findAll()).thenReturn(List.of(book));
        var result = this.bookService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        // when(this.categoryService.findAllById(anyList())).thenReturn(List.of(category));
        // when(this.publisherService.findAllById(anyList())).thenReturn(List.of(publisher));
        // when(this.authorService.findAllById(anyList())).thenReturn(List.of(author));

        // var result = this.bookService.mapToEntity(bookDto);
        // assertNotNull(result);
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
