package com.irdaislakhuafa.alterraacademyfinalproject.utils;

import java.time.LocalDateTime;

import org.springframework.validation.Errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Slf4j
public class ApiResponse<A> {
    private ApiMessage message;
    private Object error;
    private A data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static ApiResponse<?> failed(Object errors) {
        return ApiResponse.builder()
                .message(ApiMessage.FAILED)
                .data(null)
                .error(errors)
                .build();
    }

    public static ApiResponse<?> error(Object errors) {
        return ApiResponse.builder()
                .message(ApiMessage.ERROR)
                .error(errors)
                .data(null)
                .build();
    }

    public static ApiResponse<?> success(Object data) {
        return ApiResponse.builder()
                .message(ApiMessage.SUCCESS)
                .data(data)
                .error(null)
                .build();
    }

    public static ApiResponse<?> validationFailed(Object errors) {
        log.error("Validation error");
        return ApiResponse.failed(errors);
    }
}
