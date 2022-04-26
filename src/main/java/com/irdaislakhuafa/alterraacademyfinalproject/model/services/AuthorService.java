package com.irdaislakhuafa.alterraacademyfinalproject.model.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Author;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AuthorRepository;
import com.irdaislakhuafa.alterraacademyfinalproject.model.services.utils.LogMessage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService implements BaseService<Author, AuthorDto> {
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> save(Author entity) {
        LogMessage.logSave(entity);
        var savedAuthor = Optional.of(authorRepository.save(entity));
        LogMessage.logSuccessSave(entity);
        return savedAuthor;
    }

    @Override
    public Optional<Author> update(Author entity) {
        LogMessage.logUpdate(entity);
        var updatedAuthor = Optional.of(authorRepository.save(entity));
        LogMessage.logSuccessUpdate(entity);
        return updatedAuthor;
    }

    @Override
    public Optional<Author> findById(String id) {
        LogMessage.logPrepareFindById(Author.builder().id(id).build());
        var author = authorRepository.findById(id);

        if (!author.isPresent()) {
            LogMessage.logEntityFound(Author.builder().id(id).build());
            return Optional.empty();
        }

        LogMessage.logEntityFound(Author.builder().id(id).build());
        return author;
    }

    @Override
    public List<Author> findAll() {
        LogMessage.logFindAll(new Author());
        var allAuthor = authorRepository.findAll();
        LogMessage.logSuccessFindAll(new Author());
        return allAuthor;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            LogMessage.logEntityNotFound(Author.builder().id(id).build());
            return false;
        }
        authorRepository.deleteById(id);
        LogMessage.logSuccessDelete(Author.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author mapToEntity(AuthorDto dto) {
        return Author.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public List<Author> mapToEntities(List<AuthorDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Author> findAllById(List<String> ids) {
        return authorRepository.findAllById(ids);
    }

    @Override
    public List<Author> saveAll(List<Author> entities) {
        return authorRepository.saveAll(entities);
    }

}
