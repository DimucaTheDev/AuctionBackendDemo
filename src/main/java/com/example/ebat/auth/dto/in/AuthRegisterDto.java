package com.example.ebat.auth.dto.in;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Schema(description = "User registration model", contentMediaType = "application/json")
@Getter
public class AuthRegisterDto {

    @Schema(description = "Shown user name. Can not be null or empty", minLength = 3, maxLength = 20, example = "Ivan Ivanov")
    @NotEmpty
    @Length(min = 3, max = 20)
    private String shownName;

    @Schema(description = "E-Mail used for login", example = "ivanov@mail.com")
    @NotEmpty
    @Email
    private String email;

    @Schema(description = "User password", minLength = 6)
    @NotEmpty
    @Length(min = 6)
    private String password;
}
