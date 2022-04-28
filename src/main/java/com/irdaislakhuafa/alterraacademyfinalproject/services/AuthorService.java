package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.LogMessage.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AuthorDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Author;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AuthorRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService implements BaseService<Author, AuthorDto> {
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> save(Author entity) {
        logSave(entity);
        var savedAuthor = Optional.of(authorRepository.save(entity));
        logSuccessSave(entity);
        return savedAuthor;
    }

    @Override
    public Optional<Author> update(Author entity) {
        logUpdate(entity);
        var updatedAuthor = Optional.of(authorRepository.save(entity));
        logSuccessUpdate(entity);
        return updatedAuthor;
    }

    @Override
    public Optional<Author> findById(String id) {
        logPrepareFindById(Author.builder().id(id).build());
        var author = authorRepository.findById(id);

        if (!author.isPresent()) {
            logEntityFound(Author.builder().id(id).build());
            return Optional.empty();
        }

        logEntityFound(Author.builder().id(id).build());
        return author;
    }

    @Override
    public List<Author> findAll() {
        logFindAll(new Author());
        var allAuthor = authorRepository.findAll();
        logSuccessFindAll(new Author());
        return allAuthor;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            logEntityNotFound(Author.builder().id(id).build());
            return false;
        }
        authorRepository.deleteById(id);
        logSuccessDelete(Author.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author mapToEntity(AuthorDto dto) {
        logMapDtoToEntity(new Author());
        var author = Author.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
        logSuccessMapDtoToEntity(author);
        return author;
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
