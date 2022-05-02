package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.*;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.PublisherDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.PublisherRespository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@Tag(value = "publisherServiceTest")
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

    private final PublisherDto publisherDto = PublisherDto.builder()
            .name("irda islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .address(AddressDto.builder()
                    .city(address.getCity())
                    .country(address.getCountry())
                    .build())
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
        when(this.publisherRespository.findById("publisherId")).thenReturn(Optional.empty());
        var result = this.publisherService.findById("publisherId");
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.publisherRespository.existsById("publisherId")).thenReturn(true);
        var result = this.publisherService.deleteById("publisherId");
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.publisherRespository.existsById("publisherId")).thenReturn(false);
        var result = this.publisherService.deleteById("publisherId");
        assertFalse(result);
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.publisherRespository.findAll()).thenReturn(List.of(publisher));
        var result = this.publisherService.findAll();
        assertNotNull(result);
        assertEquals(List.of(publisher), result);
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        var mappedPublisher = this.publisherService.mapToEntity(publisherDto);
        assertNotNull(mappedPublisher);
        assertEquals(publisher.getEmail(), mappedPublisher.getEmail());
        assertEquals(publisher.getName(), mappedPublisher.getName());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {
        assertThrows(NullPointerException.class, () -> this.publisherService.mapToEntity(null));
    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        assertEquals(List.of(publisher)
                .get(0)
                .getEmail(),
                this.publisherService
                        .mapToEntities(List.of(publisherDto))
                        .get(0)
                        .getEmail());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {
        assertThrows(NullPointerException.class, () -> this.publisherService.mapToEntities(null));
    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.publisherRespository.saveAll(anyList())).thenReturn(List.of(publisher));
        var result = this.publisherService.saveAll(List.of(publisher));
        assertNotNull(result);
        assertEquals(List.of(publisher), result);
    }

    @Test
    @Override
    public void testSaveAllFailed() {

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.publisherRespository.findAllById(List.of("id"))).thenReturn(List.of(publisher));
        var result = this.publisherService.findAllById(List.of("id"));
        assertNotNull(result);
        assertEquals(List.of(publisher), result);
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.publisherRespository.save(any())).thenReturn(publisher);
        var result = this.publisherService.update(publisher);
        assertNotNull(result);
        assertEquals(publisher, result.get());
    }

    @Test
    @Override
    public void testUpdateFailed() {

    }

}
