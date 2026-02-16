package com.example.ebat.auth.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.validation.annotation.Validated;

@Getter
public class AuthRegisterDtoInput {

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
