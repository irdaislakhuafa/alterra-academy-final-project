package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
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
        var result = this.addressService.findAll();

        assertNotNull(result);
        assertEquals(result, List.of(address));
    }

    @Test
    public void testSaveSuccess() {
        when(this.addressRepository.save(any())).thenReturn(address);
        Optional<Address> addressOptional = this.addressService.save(address);
        assertTrue(addressOptional.isPresent());
    }

    @Test
    public void testSaveFailed() {
        Address adressTemp = null;
        when(this.addressRepository.save(adressTemp)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            this.addressService.save(adressTemp);
        });
    }

    @Test
    public void testFindByIdSuccess() {
        when(this.addressRepository.save(address)).thenReturn(address);
        when(this.addressRepository.findById(anyString())).thenReturn(Optional.of(address));

        var addressTemp = this.addressService.findById("id");

        assertNotNull(addressTemp);
        assertEquals(addressTemp.get(), address);
    }

    @Test
    public void testFindByIdFailed() {
        var result = this.addressService.findById(null);
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteByIdSuccess() {
        when(this.addressRepository.save(address)).thenReturn(address);
        var result = this.addressService.deleteById(null);
        assertFalse(result);
    }

    @Test
    public void test() {

    }

}
