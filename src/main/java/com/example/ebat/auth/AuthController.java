package com.example.ebat.auth;

import com.example.ebat.auth.dto.in.AuthLoginDtoInput;
import com.example.ebat.auth.dto.out.AuthLoginDtoOutput;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public AuthLoginDtoOutput login(@Valid @RequestBody AuthLoginDtoInput input) {
        return authService.login(input);
    }

}
