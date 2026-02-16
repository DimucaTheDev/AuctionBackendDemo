package com.example.ebat.auth.dto.in;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AuthRefreshDto {

    @NotEmpty
    private String refreshToken;

}
