package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    public Optional<Book> findByTitleEqualsIgnoreCase(String title);

    public List<Book> findByTitleContainsIgnoreCase(String title);
}
