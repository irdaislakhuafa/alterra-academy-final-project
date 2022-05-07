package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.booksBorrowing;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BooksBorrowingBook extends BooksBorrowingBase {
    @NotNull(message = "book_id cannot be null")
    @NotEmpty(message = "book_id cannot be empty")
    @NotBlank(message = "book_id cannot be blank")
    private String bookId;
}
