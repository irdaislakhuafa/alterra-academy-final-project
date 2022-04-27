package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseEntity {
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 1500)
    @Builder.Default
    private String description = "-";

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Book> books = new ArrayList<>();
}
