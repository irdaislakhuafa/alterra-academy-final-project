package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.*;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BooksBorrowingDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.*;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.BooksBorrowingRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Tag(value = "bookBorrowingServiceTest")
@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
public class BookBorrowingServiceTest implements BaseServiceTest {
    @MockBean
    private BooksBorrowingRepository booksBorrowingRepository;

    @Autowired
    private BooksBorrowingService booksBorrowingService;

    private final BooksBorrowing booksBorrowing = BooksBorrowing.builder()
            .books(List.of(new Book()))
            .students(List.of(new Student()))
            .borrowedOn(LocalDateTime.now())
            .returnedOn(null)
            .status(BorrowStatus.BORROWED)
            .build();
    private final BooksBorrowingDto booksBorrowingDto = BooksBorrowingDto.builder()
            .booksIds(List.of("book"))
            .studentsIds(List.of("student"))
            .status(BorrowStatus.BORROWED.name())
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.booksBorrowingRepository.save(any())).thenReturn(booksBorrowing);
        var result = this.booksBorrowingService.save(booksBorrowing);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {

    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.booksBorrowingRepository.findById(any())).thenReturn(Optional.of(booksBorrowing));
        var result = this.booksBorrowingService.findById("id");
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testFindByIdFailed() {
        when(this.booksBorrowingRepository.findById(any())).thenReturn(Optional.empty());
        var result = this.booksBorrowingService.findById("id");
        assertTrue(!result.isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.booksBorrowingRepository.existsById(anyString())).thenReturn(true);
        var result = this.booksBorrowingService.deleteById("id");
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.booksBorrowingRepository.existsById(anyString())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> this.booksBorrowingService.deleteById("id"));
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.booksBorrowingRepository.findAll()).thenReturn(List.of(booksBorrowing));
        var result = this.booksBorrowingService.findAll();
        assertTrue(result.size() == 1);
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        var result = this.booksBorrowingService.mapToEntity(booksBorrowingDto);
        assertEquals(BorrowStatus.BORROWED, result.getStatus());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {

    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var result = this.booksBorrowingService.mapToEntities(List.of(booksBorrowingDto));
        assertEquals(BorrowStatus.BORROWED, result.get(0).getStatus());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {

    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.booksBorrowingRepository.saveAll(anyList())).thenReturn(List.of(booksBorrowing));
        var result = this.booksBorrowingService.saveAll(List.of(booksBorrowing));
        assertTrue(result.size() == 1);
    }

    @Test
    @Override
    public void testSaveAllFailed() {

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.booksBorrowingRepository.findAllById(anyList())).thenReturn(List.of(booksBorrowing));
        var result = this.booksBorrowingService.findAllById(List.of("booksBorrowing"));
        assertTrue(result.size() == 1);
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.booksBorrowingRepository.save(any())).thenReturn(booksBorrowing);
        var result = this.booksBorrowingService.update(booksBorrowing);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testUpdateFailed() {

    }
}
