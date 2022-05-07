package com.irdaislakhuafa.alterraacademyfinalproject.model.requests;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ApiSourceTargetIdRequest extends ApiTargetIdRequest {
    @NotNull(message = "source_id cannot be null")
    @NotEmpty(message = "source_id cannot be empty")
    @NotBlank(message = "source_id cannot be blank")
    private String sourceId;
}
