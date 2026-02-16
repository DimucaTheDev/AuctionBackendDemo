package com.example.ebat.auth;

import com.example.ebat.auth.dto.in.AuthLoginDto;
import com.example.ebat.auth.dto.in.AuthRefreshDto;
import com.example.ebat.auth.dto.in.AuthRegisterDto;
import com.example.ebat.auth.dto.out.AuthResponseDto;
import com.example.ebat.auth.entity.RefreshTokenEntity;
import com.example.ebat.auth.entity.UserEntity;
import com.example.ebat.exceptions.UnauthorizedException;
import com.example.ebat.exceptions.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    private static final int REFRESH_TOKEN_DAYS = 30;

    public AuthService(AuthRepository authRepository, RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder, AuthTokenService authTokenService) {
        this.authRepository = authRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authTokenService = authTokenService;
    }

    public AuthResponseDto login(@Valid AuthLoginDto dto) {
        UserEntity userEntity = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Wrong credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPasswordHash())) {
            throw new BadCredentialsException("Wrong credentials");
        }

        return createSession(userEntity, null);
    }

    public AuthResponseDto register(@Valid AuthRegisterDto input) {
        if (authRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(input.getEmail());
        userEntity.setPasswordHash(passwordEncoder.encode(input.getPassword()));
        authRepository.save(userEntity);

        return createSession(userEntity, null);
    }

    @Transactional
    public AuthResponseDto refresh(@Valid AuthRefreshDto input) {
        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByToken(input.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (tokenEntity.getExpiration().isBefore(OffsetDateTime.now())) {
            refreshTokenRepository.delete(tokenEntity);
            throw new UnauthorizedException("Refresh token expired");
        }

        UserEntity user = tokenEntity.getUser();
        String oldDeviceInfo = tokenEntity.getDeviceInfo();

        refreshTokenRepository.delete(tokenEntity);

        return createSession(user, oldDeviceInfo);
    }

    private AuthResponseDto createSession(UserEntity user, String deviceInfo) {
        String token = authTokenService.generateToken(user.getExternalId().toString(), Map.of());
        String refreshTokenValue = UUID.randomUUID().toString();

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setDeviceInfo(deviceInfo);
        refreshTokenEntity.setIssuedAt(OffsetDateTime.now());
        refreshTokenEntity.setExpiration(OffsetDateTime.now().plusDays(REFRESH_TOKEN_DAYS));
        refreshTokenEntity.setRefreshToken(refreshTokenValue);

        refreshTokenRepository.save(refreshTokenEntity);

        AuthResponseDto dtoOut = new AuthResponseDto();
        dtoOut.setAccessToken(token);
        dtoOut.setRefreshToken(refreshTokenValue);
        dtoOut.setExpiresIn(authTokenService.getTtlSeconds());

        return dtoOut;
    }
}
