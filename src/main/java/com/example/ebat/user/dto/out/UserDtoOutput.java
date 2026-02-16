package com.example.ebat.user.dto.out;

import com.example.ebat.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "User REST API response model", contentMediaType = "application/json")
public class UserDtoOutput {

    @Schema(description = "User E-Mail", example = "ivan@mail.com")
    private String email;

    @Schema(description = "User's shown name")
    private String name;

    @Schema(description = "User's unique identifier", example = "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX")
    private UUID userId;

    @Schema(description = "User's role. Can be either BUYER or SELLER")
    private UserRole role;

    @Schema(description = "User's image avatar as a relative URL", example = "/users/profile/avatar/XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX")
    private String avatarUrl;

    @Schema(description = "Total user's balance. This includes available and frozen. Represented as a floating point number")
    private BigDecimal balance;

}
