package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.utils.BorrowStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "books_borrowing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BooksBorrowing extends BaseEntity {
    @ManyToOne(cascade = { CascadeType.ALL }, targetEntity = Book.class)
    @JoinColumn(name = "book_id", nullable = false)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    @ManyToOne(targetEntity = Student.class)
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
