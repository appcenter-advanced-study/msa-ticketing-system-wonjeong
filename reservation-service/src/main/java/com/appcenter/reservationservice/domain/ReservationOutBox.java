package com.appcenter.reservationservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationOutBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;

    private Long aggregateId;

    private String eventType;

    @Lob
    private String payload;

    private LocalDateTime createdAt;

    @Builder
    public ReservationOutBox(String aggregateType, Long aggregateId, String eventType, String payload, LocalDateTime createdAt) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.createdAt = LocalDateTime.now();
    }
}
