package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity {
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;
}
