package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryDto {
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "description cannot be null")
    @NotEmpty(message = "description cannot be empty")
    @NotBlank(message = "description cannot be blank")
    private String description;
}
