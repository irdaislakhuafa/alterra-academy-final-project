package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.booksborrowing;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BooksBorrowingStudent extends BooksBorrowingBase {
    @NotNull(message = "student_id cannot be null")
    @NotEmpty(message = "student_id cannot be empty")
    @NotBlank(message = "student_id cannot be blank")
    private String studentId;
}
