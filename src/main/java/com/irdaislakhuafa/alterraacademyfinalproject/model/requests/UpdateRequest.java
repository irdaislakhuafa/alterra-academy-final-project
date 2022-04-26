package com.irdaislakhuafa.alterraacademyfinalproject.model.requests;

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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class UpdateRequest<A> {
    @NotNull(message = "target_id cannot be null")
    @NotEmpty(message = "target_id cannot be empty")
    private String targetId;

    @NotNull(message = "data cannot be null")
    private A data;
}
