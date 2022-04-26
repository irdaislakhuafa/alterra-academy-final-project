package com.irdaislakhuafa.alterraacademyfinalproject.model.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.PublisherDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.PublisherRespository;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.utils.LogMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherService implements BaseService<Publisher, PublisherDto> {
    private final PublisherRespository publisherRepository;
    private final AddressService addressService;

    @Override
    public Optional<Publisher> save(Publisher entity) {
        LogMessage.logSave(entity);
        var savedPublisher = Optional.of(publisherRepository.save(entity));
        LogMessage.logSuccessSave(entity);
        return savedPublisher;
    }

    @Override
    public Optional<Publisher> update(Publisher entity) {
        LogMessage.logUpdate(entity);
        var updatedPublisher = Optional.of(publisherRepository.save(entity));
        LogMessage.logSuccessUpdate(entity);
        return updatedPublisher;
    }

    @Override
    public Optional<Publisher> findById(String id) {
        LogMessage.logPrepareFindById(Publisher.builder().id(id).build());
        var publisher = publisherRepository.findById(id);

        if (!publisher.isPresent()) {
            LogMessage.logEntityNotFound(Publisher.builder().id(id).build());
            return Optional.empty();
        }
        LogMessage.logEntityFound(Publisher.builder().id(id).build());
        return publisher;
    }

    @Override
    public List<Publisher> findAll() {
        LogMessage.logFindAll(new Publisher());
        var allPublisher = publisherRepository.findAll();
        LogMessage.logFindAll(new Publisher());
        return allPublisher;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            LogMessage.logEntityNotFound(Publisher.builder().id(id).build());
            return false;
        }
        publisherRepository.deleteById(id);
        LogMessage.logSuccessDelete(Publisher.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return publisherRepository.existsById(id);
    }

    @Override
    public Publisher mapToEntity(PublisherDto dto) {
        return Publisher.builder()
                .name(dto.getEmail())
                .email(dto.getEmail())
                .address(addressService.mapToEntity(dto.getAddress()))
                .build();
    }

    @Override
    public List<Publisher> mapToEntities(List<PublisherDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Publisher> findAllById(List<String> ids) {
        return publisherRepository.findAllById(ids);
    }

    @Override
    public List<Publisher> saveAll(List<Publisher> entities) {
        return publisherRepository.saveAll(entities);
    }
}
