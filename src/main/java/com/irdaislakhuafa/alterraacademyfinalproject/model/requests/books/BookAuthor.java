package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookAuthor extends BookBase {
    @NotNull(message = "author_id cannot be null")
    @NotEmpty(message = "author_id cannot be empty")
    @NotBlank(message = "author_id cannot be blank")
    private String authorId;
}
