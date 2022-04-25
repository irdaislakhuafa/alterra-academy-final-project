package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "authors")
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity {
    @Column(nullable = false, length = 100)
    @NotNull(message = "first name cannot be null")
    @NotEmpty(message = "first name cannot be empty")
    private String firstName;

    @Column(nullable = true, length = 100)
    private String lastName;

    @Column(unique = true, length = 100, nullable = false)
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email is not valid")
    private String email;

    @OneToMany
    @JoinColumn(name = "author_id")
    @Builder.Default
    private List<Author> authors = new ArrayList<>();
}
