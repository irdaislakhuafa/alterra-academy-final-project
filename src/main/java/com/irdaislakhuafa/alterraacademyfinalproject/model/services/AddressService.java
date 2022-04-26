package com.irdaislakhuafa.alterraacademyfinalproject.model.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AddressRepository;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.utils.LogMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService implements BaseService<Address, AddressDto> {
    private final AddressRepository addressRepository;

    @Override
    public Optional<Address> save(Address entity) {
        LogMessage.logSave(entity);
        var savedAddress = Optional.of(addressRepository.save(entity));
        LogMessage.logSuccessSave(entity);
        return savedAddress;
    }

    @Override
    public Optional<Address> update(Address entity) {
        LogMessage.logUpdate(entity);
        var updatedAddress = Optional.of(addressRepository.save(entity));
        LogMessage.logSuccessUpdate(entity);
        return updatedAddress;
    }

    @Override
    public Optional<Address> findById(String id) {
        LogMessage.logPrepareFindById(Address.builder().id(id).build());
        var address = addressRepository.findById(id);

        if (!address.isPresent()) {
            LogMessage.logEntityNotFound(Address.builder().id(id).build());
            return Optional.empty();
        }
        LogMessage.logEntityFound(Address.builder().id(id).build());
        return address;
    }

    @Override
    public List<Address> findAll() {
        LogMessage.logFindAll(new Address());
        var allAddress = addressRepository.findAll();
        LogMessage.logFindAll(new Address());
        return allAddress;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            LogMessage.logEntityNotFound(Address.builder().id(id).build());
            return false;
        }
        addressRepository.deleteById(id);
        LogMessage.logSuccessDelete(Address.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return addressRepository.existsById(id);
    }

    @Override
    public Address mapToEntity(AddressDto dto) {
        LogMessage.logMapDtoToEntity(new Address());
        var address = Address.builder()
                .city(dto.getCity())
                .country(dto.getCountry())
                .build();
        LogMessage.logSuccessMapDtoToEntity(address);
        return address;
    }

    @Override
    public List<Address> mapToEntities(List<AddressDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Address> findAllById(List<String> ids) {
        return addressRepository.findAllById(ids);
    }

    @Override
    public List<Address> saveAll(List<Address> entities) {
        return addressRepository.saveAll(entities);
    }

}
