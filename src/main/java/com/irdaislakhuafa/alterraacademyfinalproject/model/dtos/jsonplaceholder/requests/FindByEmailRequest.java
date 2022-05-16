package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder.requests;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FindByEmailRequest {
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email is not valid")
    private String email;
}
