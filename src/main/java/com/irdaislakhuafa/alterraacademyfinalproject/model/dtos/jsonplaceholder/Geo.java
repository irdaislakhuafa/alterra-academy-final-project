package com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Geo {
    private String lat;
    private String lng;
}
