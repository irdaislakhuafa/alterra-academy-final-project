package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.books;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookPublisher extends BookBase {
    @NotNull(message = "publisher_id cannot be null")
    @NotEmpty(message = "publisher_id cannot be empty")
    @NotBlank(message = "publisher_id cannot be blank")
    private String publisherId;
}
