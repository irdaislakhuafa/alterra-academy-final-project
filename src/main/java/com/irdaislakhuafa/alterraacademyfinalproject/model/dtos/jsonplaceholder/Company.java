package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}
