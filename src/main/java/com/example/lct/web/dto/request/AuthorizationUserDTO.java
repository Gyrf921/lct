package com.example.lct.web.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationUserDTO {

    //@NotNull
    //@Email
    private String email;

    //@NotNull
    //@Length(min = 2, max = 50, message = "firstName length must be from 2 to 30")
    private String password;
}
