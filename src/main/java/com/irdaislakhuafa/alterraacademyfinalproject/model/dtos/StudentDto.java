package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentDto {
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email is not valid")
    private String email;

    @NotNull(message = "semester cannot be null")
    private Byte semester;

    @NotNull(message = "batch_of_years cannot be null")
    private Integer batchOfYears;
}
