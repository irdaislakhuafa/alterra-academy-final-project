package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "books_borrowing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BooksBorrowing extends BaseEntity {
    @ManyToMany
    @JoinColumn(name = "book_id", nullable = false)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "student_id", nullable = false)
    @Builder.Default
    private List<Student> students = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status;

    @Column(nullable = false)
    private LocalDateTime borrowedOn;

    private LocalDateTime returnedOn;

    @PrePersist
    public void onInsert() {
        this.borrowedOn = LocalDateTime.now();
    }
}
