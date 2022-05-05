package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookAuthor {
    @NotNull(message = "book_id cannot be null")
    @NotEmpty(message = "book_id cannot be empty")
    @NotBlank(message = "book_id cannot be blank")
    private String bookId;

    @NotNull(message = "author_id cannot be null")
    @NotEmpty(message = "author_id cannot be empty")
    @NotBlank(message = "author_id cannot be blank")
    private String authorId;
}
