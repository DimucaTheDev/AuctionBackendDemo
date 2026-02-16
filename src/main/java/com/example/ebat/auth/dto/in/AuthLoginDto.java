package com.example.ebat.auth.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Schema(description = "Authentication user login model", contentMediaType = "application/json")
@Getter
public class AuthLoginDto {

    @Schema(description = "User's E-Mail used as credentials")
    @NotEmpty
    @Email
    private String email;

    @Schema(description = "User's password")
    @NotEmpty
    private String password;

}
