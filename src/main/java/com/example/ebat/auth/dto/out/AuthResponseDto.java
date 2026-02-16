package com.example.ebat.auth.dto.out;

import com.example.ebat.auth.AuthTokenService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Authentication response model", contentMediaType = "application/json")
public class AuthResponseDto {

    @Schema(description = "Access token. It's lifetime is limited to 5 minutes") //todo: update time if needed
    private String accessToken;

    @Schema(description = "Refresh token. It's lifetime is limited to 30 days")
    private String refreshToken;

    @Schema(description = "Access token lifetime")
    private Long expiresIn;

}
