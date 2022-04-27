package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.PublisherDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.PublisherRespository;
import static com.irdaislakhuafa.alterraacademyfinalproject.services.utils.LogMessage.*;

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
        logSave(entity);
        var savedPublisher = Optional.of(publisherRepository.save(entity));
        logSuccessSave(entity);
        return savedPublisher;
    }

    @Override
    public Optional<Publisher> update(Publisher entity) {
        logUpdate(entity);
        var updatedPublisher = Optional.of(publisherRepository.save(entity));
        logSuccessUpdate(entity);
        return updatedPublisher;
    }

    @Override
    public Optional<Publisher> findById(String id) {
        logPrepareFindById(Publisher.builder().id(id).build());
        var publisher = publisherRepository.findById(id);

        if (!publisher.isPresent()) {
            logEntityNotFound(Publisher.builder().id(id).build());
            return Optional.empty();
        }
        logEntityFound(Publisher.builder().id(id).build());
        return publisher;
    }

    @Override
    public List<Publisher> findAll() {
        logFindAll(new Publisher());
        var allPublisher = publisherRepository.findAll();
        logFindAll(new Publisher());
        return allPublisher;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            logEntityNotFound(Publisher.builder().id(id).build());
            return false;
        }
        publisherRepository.deleteById(id);
        logSuccessDelete(Publisher.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return publisherRepository.existsById(id);
    }

    @Override
    public Publisher mapToEntity(PublisherDto dto) {
        logMapDtoToEntity(new Publisher());
        var publisher = Publisher.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(addressService.mapToEntity(dto.getAddress()))
                .build();
        logSuccessMapDtoToEntity(publisher);
        return publisher;
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
