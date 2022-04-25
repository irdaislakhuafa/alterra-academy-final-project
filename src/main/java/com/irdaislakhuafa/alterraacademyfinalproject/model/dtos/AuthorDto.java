package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthorDto {
    @NotNull(message = "first name cannot be null")
    @NotEmpty(message = "first name cannot be empty")
    @Max(value = 100, message = "max character for first name is 100")
    private String firstName;

    @Max(value = 100, message = "max character for last name is 100")
    private String lastName;

    @Max(value = 100, message = "max character for email is 100")
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email is not valid")
    private String email;

    @Builder.Default
    private List<String> addressId = new ArrayList<>();
}
