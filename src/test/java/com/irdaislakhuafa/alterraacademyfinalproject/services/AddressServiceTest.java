package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

// @ExtendWith(value = { MockitoExtension.class })
@SpringBootTest
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository = mock(AddressRepository.class);

    @InjectMocks
    private AddressService addressService = mock(AddressService.class);

    @Test
    public void testNotNull() {
        assertNotNull(addressService);
    }

    @Test
    public void testFindAllSuccess() {

        assertNotNull(addressRepository);
        assertNotNull(addressService);
        assertNotNull(addressService.findAll());
        when(addressService.findAll()).thenReturn(List.of(new Address()));
    }

    @Test
    public void testSaveSuccess() {
        var address = Address.builder()
                .id("id")
                .city("Tuban")
                .country("indonesia")
                .build();
        var result = this.addressService.save(address);

        when(result).thenReturn(Optional.empty());
    }
}
