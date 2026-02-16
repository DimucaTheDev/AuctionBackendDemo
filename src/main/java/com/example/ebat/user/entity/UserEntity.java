package com.example.ebat.user.entity;

import com.example.ebat.auth.entity.RefreshTokenEntity;
import com.example.ebat.lots.entity.LotEntity;
import com.example.ebat.user.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "shown_name", nullable = false)
    private String shownName;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RefreshTokenEntity> refreshTokens;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LotEntity> lots;

    @Column(name = "available_balance", nullable = false)
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Column(name = "frozen_balance", nullable = false)
    private BigDecimal frozenBalance = BigDecimal.ZERO;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private UserRole role;

    public BigDecimal getTotalBalance() {
        return availableBalance.add(frozenBalance);
    }
}