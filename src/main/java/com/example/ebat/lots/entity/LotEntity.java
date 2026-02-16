package com.example.ebat.lots.entity;

import com.example.ebat.user.entity.UserEntity;
import com.example.ebat.lots.LotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "lots")
@Getter
@Setter
public class LotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", updatable = false, nullable = false)
    private UserEntity createdBy;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @Column(name = "minimal_bid_step", nullable = false)
    private BigDecimal minBidStep;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highest_bid_id")
    private BidEntity highestBid;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LotStatus status;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BidEntity> bids;
}