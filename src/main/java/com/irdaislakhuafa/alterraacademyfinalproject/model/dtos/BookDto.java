package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookDto {
    @NotNull(message = "title cannot be null")
    @NotEmpty(message = "title cannot be empty")
    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotNull(message = "description cannot be null")
    @NotEmpty(message = "description cannot be empty")
    @NotBlank(message = "description cannot be blank")
    private String description;

    @Builder.Default
    @NotNull(message = "author_ids cannot be null")
    private List<String> authorIds = new ArrayList<>();

    @Builder.Default
    @NotNull(message = "publisher_ids cannot be null")
    private List<String> publisherIds = new ArrayList<>();

    @Builder.Default
    @NotNull(message = "category_ids cannot be null")
    private List<String> categoryIds = new ArrayList<>();
}
