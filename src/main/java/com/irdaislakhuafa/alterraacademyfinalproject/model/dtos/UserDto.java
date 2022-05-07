package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email is not valid")
    private String email;

    @Builder.Default
    @NotNull(message = "roles[] cannot be null")
    @NotEmpty(message = "roles[] cannot be empty")
    private List<@NotNull(message = "roles cannot be null") @NotEmpty(message = "roles cannot be empty") @NotBlank(message = "roles cannot be blank") String> roles = new ArrayList<>();
}
