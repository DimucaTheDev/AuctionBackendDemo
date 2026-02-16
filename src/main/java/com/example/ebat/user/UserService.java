package com.example.ebat.user;

import com.example.ebat.auth.AuthRepository;
import com.example.ebat.user.dto.out.UserDtoOutput;
import com.example.ebat.user.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.UUID;

@Service
public class UserService {

    private final AuthRepository authRepository;

    public UserService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Transactional(readOnly = true)
    public UserDtoOutput getUserProfile(Authentication principal) {
        UserEntity user = authRepository.findByExternalId(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDtoOutput dtoOut = new UserDtoOutput();
        dtoOut.setName(user.getShownName());
        dtoOut.setBalance(user.getTotalBalance());
        dtoOut.setEmail(user.getEmail());
        dtoOut.setRole(user.getRole());
        dtoOut.setAvatarUrl(user.getAvatarUrl());

        return dtoOut;
    }
}
