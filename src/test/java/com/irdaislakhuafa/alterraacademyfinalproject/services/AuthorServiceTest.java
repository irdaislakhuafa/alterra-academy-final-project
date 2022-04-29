package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.List;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.*;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address.AddressBuilder;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = { AuthorService.class })
public class AuthorServiceTest {
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AddressService addressService;

    private final Address address = Address.builder()
            .id("addressId")
            .city("tuban")
            .country("indonesia")
            .build();

    private final Category category = Category.builder()
            .id("categoryId")
            .name("category 1")
            .description("-")
            .build();

    private final Book book = Book.builder()
            .id("bookId")
            .title("book 1")
            .description("-")
            .categories(List.of(category))
            .build();

    private final Author author = Author.builder()
            .id("id")
            .firstName("irda")
            .lastName("islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .address(List.of(address))
            .books(List.of(book))
            .build();

}
