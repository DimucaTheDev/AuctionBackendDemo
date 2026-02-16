package com.example.ebat.auth.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AuthLoginDtoInput {

    @NotEmpty
    @Email
    private String email;

}
