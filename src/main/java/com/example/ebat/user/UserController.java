package com.example.ebat.user;

import com.example.ebat.user.dto.out.UserDtoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user profile", description = "Returns user profile information for current access token")
    @GetMapping("/profile")
    @SecurityRequirement(name = "BearerAuth")
    @PreAuthorize("isAuthenticated()")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User does not exist")
    })
    public UserDtoOutput getUserProfile(Authentication authentication) {
        return userService.getUserProfile(authentication);
    }

    @Operation(summary = "Get user avatar")
    @GetMapping(value = "/profile/avatar/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Avatar not found")
    })
    public byte[] getAvatar(@PathVariable("uuid") String uuid) {
        try {
            Path path = Paths.get("avatars", uuid + ".png");

            if (!Files.exists(path)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar not found");
            }

            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading avatar file", e);
        }
    }
}
