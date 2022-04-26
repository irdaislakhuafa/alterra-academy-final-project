package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

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
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class AddressDto {
    @NotNull(message = "city cannot be null")
    @NotEmpty(message = "city cannot be empty")
    private String city;

    @NotNull(message = "country cannot be null")
    @NotEmpty(message = "country cannot be empty")
    private String country;
}
