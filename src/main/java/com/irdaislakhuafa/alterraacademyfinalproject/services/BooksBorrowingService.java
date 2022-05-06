package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.LogMessage.*;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BooksBorrowingDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.BooksBorrowing;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.BooksBorrowingRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BooksBorrowingService implements BaseService<BooksBorrowing, BooksBorrowingDto> {

    private final BooksBorrowingRepository booksBorrowingRepository;
    private final BookService bookService;
    private final StudentService studentService;

    @Override
    public Optional<BooksBorrowing> save(BooksBorrowing entity) {
        logSave(entity);
        var saved = this.booksBorrowingRepository.save(entity);
        logSuccessSave(entity);
        return Optional.of(saved);
    }

    @Override
    public Optional<BooksBorrowing> update(BooksBorrowing entity) {
        logUpdate(entity);
        var updated = this.booksBorrowingRepository.save(entity);
        logSuccessUpdate(entity);
        return Optional.of(updated);
    }

    @Override
    public Optional<BooksBorrowing> findById(String id) {
        final BooksBorrowing tempValue = BooksBorrowing.builder().id(id).build();
        logPrepareFindById(tempValue);
        var value = this.booksBorrowingRepository.findById(id);
        if (!value.isPresent()) {
            logEntityNotFound(tempValue);
            return Optional.empty();
        }
        logEntityFound(tempValue);
        return value;
    }

    @Override
    public List<BooksBorrowing> findAll() {
        final BooksBorrowing tempValue = BooksBorrowing.builder().build();
        logFindAll(tempValue);
        var value = this.booksBorrowingRepository.findAll();
        logSuccessFindAll(tempValue);
        return value;
    }

    @Override
    public boolean deleteById(String id) {
        final BooksBorrowing tempValue = BooksBorrowing.builder().id(id).build();
        if (!this.existsById(id)) {
            logEntityNotFound(tempValue);
            throw new NoSuchElementException("books borrowinf with id: " + id + " not found");
        }
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return this.booksBorrowingRepository.existsById(id);
    }

    @Override
    public BooksBorrowing mapToEntity(BooksBorrowingDto dto) {
        final BooksBorrowing tempValue = BooksBorrowing.builder().build();
        logMapDtoToEntity(tempValue);
        var value = BooksBorrowing.builder()
                .status(dto.getStatus())
                .books(this.bookService.findAllById(dto.getBooksIds()))
                .students(this.studentService.findAllById(dto.getStudentsIds()))
                .build();
        logSuccessMapDtoToEntity(tempValue);
        return value;
    }

    @Override
    public List<BooksBorrowing> mapToEntities(List<BooksBorrowingDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<BooksBorrowing> findAllById(List<String> ids) {
        return this.booksBorrowingRepository.findAllById(ids);
    }

    @Override
    public List<BooksBorrowing> saveAll(List<BooksBorrowing> entities) {
        return this.booksBorrowingRepository.saveAll(entities);
    }

}
