package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.PublisherRespository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest
public class PublisherServiceTest implements BaseServiceTest {
    @MockBean
    private PublisherRespository publisherRespository;

    @Autowired
    private PublisherService publisherService;

    private final Address address = Address.builder()
            .id("addressId")
            .city("tuban")
            .country("indonesia")
            .build();

    private final Publisher publisher = Publisher.builder()
            .id("publisherId")
            .name("irda islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .address(address)
            .books(new ArrayList<>())
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.publisherRespository.save(any())).thenReturn(publisher);
        var result = this.publisherService.save(publisher);
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {
        when(this.publisherRespository.save(any())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> this.publisherService.save(publisher));
    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.publisherRespository.findById("publisherId")).thenReturn(Optional.of(publisher));
        var result = this.publisherService.findById("publisherId");
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testFindByIdFailed() {

    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {

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
