package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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

    // @MockBean
    // private AuthorService authorService;

    // @MockBean
    // private PublisherService publisherService;

    // @MockBean
    // private CategoryService categoryService;

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
            .title("book 1")
            .description("-")
            .authors(List.of(author))
            .publishedDate(new Date())
            // .categories(List.of(category))
            // .publishers(List.of(publisher))
            .build();

    private final BookDto bookDto = BookDto.builder()
            .title("book 1")
            .description("-")
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
        var result = this.bookService.mapToEntity(bookDto);
        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {

    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var result = this.bookService.mapToEntities(List.of(bookDto));
        assertNotNull(result);
        assertEquals(book.getTitle(), result.get(0).getTitle());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {

    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.bookRepository.saveAll(anyList())).thenReturn(List.of(book));
        var result = this.bookService.saveAll(List.of(book));
        assertNotNull(result);
    }

    @Test
    @Override
    public void testSaveAllFailed() {

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.bookRepository.findAllById(List.of(book.getId()))).thenReturn(List.of(book));
        var result = this.bookService.findAllById(List.of(book.getId()));
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(this.bookRepository.save(any())).thenReturn(book);
        var result = this.bookService.update(book);
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testUpdateFailed() {

    }

    @Test
    public void findByTitleSuccess() {
        when(this.bookRepository.findByTitleEqualsIgnoreCase(anyString())).thenReturn(Optional.of(book));
        var result = this.bookService.findByTitle("title");
        assertNotNull(result);
    }

    @Test
    public void findByTitleFailed() {
        when(this.bookRepository.findByTitleEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        var result = this.bookService.findByTitle("title");
        assertNotNull(result);
    }

    @Test
    public void findByTitleContains() {
        when(this.bookRepository.findByTitleContainsIgnoreCase(anyString())).thenReturn(List.of(book));
        var result = this.bookService.findByTitleContains("title");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
