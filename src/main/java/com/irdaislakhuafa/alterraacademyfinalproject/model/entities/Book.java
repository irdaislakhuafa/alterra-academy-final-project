package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Book extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 1500)
    private String description;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private Date publishedDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @ManyToMany
    @JoinColumn(nullable = false)
    @Builder.Default
    private List<Author> authors = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<Publisher> publishers = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    @PrePersist
    public void onInsert() {
        this.publishedDate = new Date();
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
