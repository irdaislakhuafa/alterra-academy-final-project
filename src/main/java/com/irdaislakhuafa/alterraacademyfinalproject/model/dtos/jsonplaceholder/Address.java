package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
}
