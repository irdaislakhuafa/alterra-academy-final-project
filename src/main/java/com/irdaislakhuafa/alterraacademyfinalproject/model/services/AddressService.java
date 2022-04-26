package com.irdaislakhuafa.alterraacademyfinalproject.model.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressService implements BaseService<Address, AddressDto> {
    private final AddressRepository addressRepository;

    @Override
    public Optional<Address> save(Address entity) {
        log.info("Saving new address");
        var savedAddress = Optional.of(addressRepository.save(entity));
        log.info("Success saveing new address");
        return savedAddress;
    }

    @Override
    public Optional<Address> update(Address entity) {
        log.info("Updating address id: " + entity.getId());
        var updatedAddress = Optional.of(addressRepository.save(entity));
        log.info("Success updating address id: " + entity.getId());
        return updatedAddress;
    }

    @Override
    public Optional<Address> findById(String id) {
        log.info("Preparing get address with id: " + id);
        var address = addressRepository.findById(id);

        if (!address.isPresent()) {
            log.info("Address with id: " + id + " not found");
            return Optional.empty();
        }
        log.info("Found address with id: " + id);
        return address;
    }

    @Override
    public List<Address> findAll() {
        log.info("Find all address");
        var allAddress = addressRepository.findAll();
        log.info("Success get all address");
        return allAddress;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            log.info("Address with id: " + id + " not found");
            return false;
        }
        log.info("Success delete address id: " + id);
        addressRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return addressRepository.existsById(id);
    }

    @Override
    public Address mapToEntity(AddressDto dto) {
        log.info("Mapping address dto to entity");
        var address = Address.builder()
                .city(dto.getCity())
                .country(dto.getCountry())
                .build();
        log.info("Success address dto to entity");
        return address;
    }

    @Override
    public List<Address> mapToEntities(List<AddressDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

}
