package com.example.ebat.auth.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AuthLoginDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

}
