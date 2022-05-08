package com.irdaislakhuafa.alterraacademyfinalproject.model.requests.users;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserLoginRequest {
    private String email;
    private String password;
}
