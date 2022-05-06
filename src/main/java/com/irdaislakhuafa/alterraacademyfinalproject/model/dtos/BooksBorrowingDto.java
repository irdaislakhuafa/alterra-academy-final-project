package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos;

import java.util.List;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BooksBorrowingDto {
    private List<String> booksIds;
    private List<String> studentsIds;
    private BorrowStatus status;
}
