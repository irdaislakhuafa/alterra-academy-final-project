package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import java.util.List;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BooksBorrowingDto {
    @NotNull(message = "books_ids[] cannot be null")
    @NotEmpty(message = "books_ids[] cannot be empty")
    private List<@NotNull(message = "books_ids cannot be null") @NotEmpty(message = "books_ids cannot be empty") @NotBlank(message = "books_ids cannot be blank") String> booksIds;

    @NotNull(message = "students_ids cannot be null")
    @NotEmpty(message = "students_ids cannot be empty")
    private List<@NotNull(message = "students_ids cannot be null") @NotEmpty(message = "students_ids cannot be empty") @NotBlank(message = "students_ids cannot be blank") String> studentsIds;

    @NotNull(message = "status cannot be null")
    @NotEmpty(message = "status cannot be empty")
    @NotBlank(message = "status cannot be blank")
    private BorrowStatus status;
}
