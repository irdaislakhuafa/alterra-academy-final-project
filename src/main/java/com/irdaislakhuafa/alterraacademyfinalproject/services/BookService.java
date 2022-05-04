package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.LogMessage.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.BookDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Book;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.BookRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService implements BaseService<Book, BookDto> {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;

    @Override
    public Optional<Book> save(Book entity) {
        logSave(entity);
        var savedBook = Optional.of(bookRepository.save(entity));
        logSuccessSave(entity);
        return savedBook;
    }

    @Override
    public Optional<Book> update(Book entity) {
        logUpdate(entity);
        var book = this.bookRepository.findById(entity.getId());

        if (!book.isPresent()) {
            throw new NoSuchElementException("Book with id: " + entity.getId() + " not found");
        }
        var updatedBook = Optional.of(bookRepository.save(entity));
        logSuccessUpdate(entity);
        return updatedBook;
    }

    @Override
    public Optional<Book> findById(String id) {
        logPrepareFindById(Book.builder().id(id).build());
        var book = bookRepository.findById(id);

        if (!book.isPresent()) {
            logEntityNotFound(Book.builder().id(id).build());
            return Optional.empty();
        }
        logEntityFound(Book.builder().id(id).build());
        return book;
    }

    @Override
    public List<Book> findAll() {
        logFindAll(new Book());
        var allBook = bookRepository.findAll();
        logFindAll(new Book());
        return allBook;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            logEntityNotFound(Book.builder().id(id).build());
            return false;
        }
        bookRepository.deleteById(id);
        logSuccessDelete(Book.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Book mapToEntity(BookDto dto) {
        logMapDtoToEntity(new Book());
        var book = Book.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .authors(this.authorService.findAllById(dto.getAuthorIds()))
                .publishers(this.publisherService.findAllById(dto.getPublisherIds()))
                .categories(this.categoryService.findAllById(dto.getCategoryIds()))
                .build();
        logSuccessMapDtoToEntity(book);
        return book;
    }

    @Override
    public List<Book> mapToEntities(List<BookDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllById(List<String> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public List<Book> saveAll(List<Book> entities) {
        return bookRepository.saveAll(entities);
    }

    public Optional<Book> findByTitle(String title) {
        logPrepareFindByName(Book.builder().build(), title);
        var book = bookRepository.findByTitleEqualsIgnoreCase(title);

        if (!book.isPresent()) {
            logEntityNotFound(Book.builder().build());
            return Optional.empty();
        }
        logEntityFound(Book.builder().build());
        return book;
    }

    public List<Book> findByTitleContains(String title) {
        var books = this.bookRepository.findByTitleContainsIgnoreCase(title);
        return books;
    }

}
