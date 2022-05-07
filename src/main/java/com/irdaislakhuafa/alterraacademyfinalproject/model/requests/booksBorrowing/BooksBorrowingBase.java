package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.booksborrowing;

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
public class BooksBorrowingBase {
    @NotNull(message = "books_borrowing_id cannot be null")
    @NotEmpty(message = "books_borrowing_id cannot be empty")
    @NotBlank(message = "books_borrowing_id cannot be blank")
    private String booksBorrowingId;
}
