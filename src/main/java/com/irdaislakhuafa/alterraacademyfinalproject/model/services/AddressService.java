package com.irdaislakhuafa.alterraacademyfinalproject.model.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService implements BaseService<Address, AddressDto> {
    private final AddressRepository addressRepository;

    @Override
    public Optional<Address> save(Address entity) {
        return null;
    }

    @Override
    public Optional<Address> update(Address entity) {
        return null;
    }

    @Override
    public Optional<Address> findById(String id) {
        return null;
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Address mapToEntity(AddressDto dto) {
        return null;
    }

    @Override
    public List<Address> mapToEntities(List<AddressDto> dtos) {
        return null;
    }

}
