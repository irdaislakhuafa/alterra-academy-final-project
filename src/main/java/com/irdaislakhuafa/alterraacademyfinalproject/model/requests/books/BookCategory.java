package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookCategory extends BookBase {
    @NotNull(message = "category_id cannot be null")
    @NotEmpty(message = "category_id cannot be empty")
    @NotBlank(message = "category_id cannot be blank")
    private String categoryId;
}
