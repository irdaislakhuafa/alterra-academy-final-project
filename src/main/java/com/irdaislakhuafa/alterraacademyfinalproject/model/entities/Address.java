package com.irdaislakhuafa.alterraacademyfinalproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {
    @Column(length = 100, nullable = false)
    @NotNull(message = "city cannot be null")
    @NotEmpty(message = "city cannot be empty")
    private String city;

    @NotNull(message = "country cannot be null")
    @NotEmpty(message = "country cannot be empty")
    @Column(length = 100, nullable = false)
    private String country;
}
