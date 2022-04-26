package com.irdaislakhuafa.alterraacademyfinalproject.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApiResponse<A> {
    private ApiMessage message;
    private Object error;
    private A data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
