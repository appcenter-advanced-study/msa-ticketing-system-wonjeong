package com.appcenter.reservationservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String username;

    private Long ticketId;

    public Reservation(String username, Long ticketId) {
        this.username = username;
        this.ticketId = ticketId;
    }

    public void updateReservation(String username, Long ticketId) {
        this.ticketId = ticketId != null ? ticketId : this.ticketId;
        this.username = username != null ? username : this.username;
    }
}
