package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

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
    private String name;
    private String description;
}
