package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest
public class AddressServiceTest implements BaseServiceTest {

    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    private final Address address = Address.builder()
            .id("id")
            .city("tuban")
            .country("indonesia")
            .build();

    private final AddressDto addressDto = AddressDto.builder()
            .city("tuban")
            .country("indonesia")
            .build();

    @Test
    public void testNotNull() {
        assertNotNull(addressRepository);
        assertNotNull(addressService);
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.addressRepository.findAll()).thenReturn(List.of(address));
        var result = this.addressService.findAll();

        assertNotNull(result);
        assertEquals(result, List.of(address));
    }

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.addressRepository.save(any())).thenReturn(address);
        Optional<Address> addressOptional = this.addressService.save(address);
        assertTrue(addressOptional.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {
        Address adressTemp = null;
        when(this.addressRepository.save(adressTemp)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            this.addressService.save(adressTemp);
        });
    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.addressRepository.findById(anyString()))
                .thenReturn(Optional.of(address));

        var addressTemp = this.addressService.findById("id");

        assertNotNull(addressTemp);
        assertEquals(addressTemp.get(), address);
    }

    @Test
    @Override
    public void testFindByIdFailed() {
        var result = this.addressService.findById(null);
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.addressRepository.existsById("id")).thenReturn(true);
        var result = this.addressService.deleteById("id");
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.addressRepository.existsById("id")).thenReturn(false);
        var result = this.addressService.deleteById("id");
        assertFalse(result);
    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.addressRepository.saveAll(anyList())).thenReturn(List.of(address));
        var result = this.addressService.saveAll(List.of(address));
        assertNotNull(result);
        assertEquals(List.of(address), result);
    }

    @Test
    @Override
    public void testSaveAllFailed() {
        when(this.addressRepository.saveAll(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> this.addressService.saveAll(null));
    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.addressRepository.findAllById(anyList()))
                .thenReturn(List.of(address));

        var result = this.addressService.findAllById(List.of("id"));
        assertEquals(List.of(address), result);
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.addressRepository.save(address)).thenReturn(address);
        var result = this.addressService.update(address);
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testUpdateFailed() {
        when(this.addressRepository.save(any(Address.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> this.addressService.update(address));
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        assertNotNull(addressDto);
        assertNotNull(address);

        var mappedAddress = this.addressService.mapToEntity(addressDto);

        assertEquals(address.getCity(), mappedAddress.getCity());
        assertEquals(address.getCountry(), mappedAddress.getCountry());
        assertEquals(address.getClass(), mappedAddress.getClass());
    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var mappedAddresses = this.addressService.mapToEntities(List.of(addressDto));

        assertNotNull(mappedAddresses);

        mappedAddresses.forEach((value) -> assertEquals(value.getCity(), addressDto.getCity()));
    }

    public void testMapToEntityFailed() {

    }

    @Override
    public void testMapToEntitiesFailed() {

    }
}
