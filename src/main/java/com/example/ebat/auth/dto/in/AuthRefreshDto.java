package com.example.ebat.auth.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Schema(description = "Access and Refresh token updating model", contentMediaType = "application/json")
@Getter
public class AuthRefreshDto {

    @Schema(description = "Refresh token")
    @NotEmpty
    private String refreshToken;

}
