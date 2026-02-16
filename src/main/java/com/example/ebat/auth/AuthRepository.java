package com.example.ebat.auth;

import com.example.ebat.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public class AuthRepository implements JpaRepository<Long, UserEntity> {
}
