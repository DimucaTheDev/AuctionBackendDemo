package com.example.ebat.lots.entity;

import com.example.ebat.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bids", indexes = {
    @Index(name = "idx_bid_lot_id", columnList = "lot_id"),
    @Index(name = "idx_bid_bidder_id", columnList = "bidder_id")
})
@Getter
@Setter
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true, nullable = false, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false)
    private LotEntity lot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    private UserEntity bidder;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
