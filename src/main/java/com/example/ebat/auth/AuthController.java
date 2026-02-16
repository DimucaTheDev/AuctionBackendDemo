package com.example.ebat.auth;

import com.example.ebat.auth.dto.in.AuthLoginDto;
import com.example.ebat.auth.dto.in.AuthRefreshDto;
import com.example.ebat.auth.dto.in.AuthRegisterDto;
import com.example.ebat.auth.dto.out.AuthResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody AuthLoginDto input) {
        return authService.login(input);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody AuthRegisterDto input) {
        return authService.register(input);
    }

    @PostMapping("/refresh")
    public AuthResponseDto refresh(@Valid @RequestBody AuthRefreshDto input) {
        return authService.refresh(input);
    }

}
