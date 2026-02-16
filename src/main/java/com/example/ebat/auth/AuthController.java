package com.example.ebat.auth;

import com.example.ebat.auth.dto.in.AuthLoginDto;
import com.example.ebat.auth.dto.in.AuthRefreshDto;
import com.example.ebat.auth.dto.in.AuthRegisterDto;
import com.example.ebat.auth.dto.out.AuthResponseDto;
import com.example.ebat.user.dto.out.UserDtoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Log in to user account", description = "Logs in to user account with provided E-Mail and password, and returns access and refresh tokens")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    public AuthResponseDto login(@Valid @RequestBody AuthLoginDto input) {
        return authService.login(input);
    }

    @Operation( summary = "Registers a new user account", description = "Creates new user account with provided properties, creates random avatar, and returns access and refresh tokens")
    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "User with same credentials already exists"),
            @ApiResponse(responseCode = "500", description = "Server error occurred")
    })
    public AuthResponseDto register(@Valid @RequestBody AuthRegisterDto input) {
        return authService.register(input);
    }

    @Operation(summary = "Refreshes access token", description = "Deletes old access and refresh tokens and returns new tokens")
    @PostMapping("/refresh")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Provided refresh token is invalid"),
            @ApiResponse(responseCode = "401", description = "Provided refresh token is expired"),
    })
    public AuthResponseDto refresh(@Valid @RequestBody AuthRefreshDto input) {
        return authService.refresh(input);
    }

    @Operation(summary = "Logs out of user account", description = "Makes provided access token invalid")
    @PostMapping("/logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public void logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.logout(token);
        }
    }

    @GetMapping("/test")
    @SecurityRequirement(name = "BearerAuth")
    @PreAuthorize("isAuthenticated()")
    public String test() { return "hi"; }
}
