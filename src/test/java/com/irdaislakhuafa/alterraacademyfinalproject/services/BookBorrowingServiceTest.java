package com.irdaislakhuafa.alterraacademyfinalproject.services;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
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

    @Override
    public void testSaveSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testSaveFailed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testFindByIdSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testFindByIdFailed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testDeleteByIdSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testDeleteByIdFailed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testFindAllSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testMapToEntitySuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testMapToEntityFailed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testMapToEntitiesSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testMapToEntitiesFailed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testSaveAllSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testSaveAllFailed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testFindAllByIdSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testUpdateSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void testUpdateFailed() {
        // TODO Auto-generated method stub

    }
}
