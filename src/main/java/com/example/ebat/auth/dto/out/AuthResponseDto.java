package com.example.ebat.auth.dto.out;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;

}
