package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookBase {
    @NotNull(message = "book_id cannot be null")
    @NotEmpty(message = "book_id cannot be empty")
    @NotBlank(message = "book_id cannot be blank")
    private String bookId;
}
