package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AddressServiceTest {

    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    private final Address address = Address.builder()
            .id("id")
            .city("Tuban")
            .country("indonesia")
            .build();

    @Test
    public void testNotNull() {
        assertNotNull(addressRepository);
        assertNotNull(addressService);
    }

    @Test
    public void testFindAllSuccess() {
        when(this.addressRepository.findAll()).thenReturn(List.of(address));
    }

    @Test
    public void testSaveSuccess() {
        when(this.addressRepository.save(any())).thenReturn(address);
        Optional<Address> addressOptional = this.addressService.save(address);
        assertTrue(addressOptional.isPresent());
    }

    @Test
    public void testSaveFailed() {
        var adressTemp = address;
        adressTemp.setCity(null);

        assertNull(adressTemp.getCity());
        when(this.addressService.save(adressTemp)).thenThrow(NullPointerException.class);
    }
}