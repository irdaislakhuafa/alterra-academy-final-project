package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@FilterDef(name = "deletedBookFilter", parameters = {
        @ParamDef(name = "isDeleted", type = "boolean")
})
@Filter(name = "deletedBookFilter", condition = "is_deleted = :isDeleted")
public class Book extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 1500)
    private String description;

    @Column(nullable = false, name = "is_deleted")
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
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @Builder.Default
    private Set<Publisher> publishers = new HashSet<>();

    @ManyToMany
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

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
