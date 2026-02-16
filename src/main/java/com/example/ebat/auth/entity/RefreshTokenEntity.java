package com.example.ebat.auth.entity;

import ch.qos.logback.core.model.INamedModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime expiration;

    private String deviceInfo;

    @Column(name = "issued_at", nullable = false)
    private OffsetDateTime issuedAt;

}
