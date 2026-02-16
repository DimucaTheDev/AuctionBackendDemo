package com.example.ebat.auth.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class AuthRegisterDto {

    @NotEmpty
    @Length(min = 3, max = 20)
    private String shownName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 6)
    private String password;
}
