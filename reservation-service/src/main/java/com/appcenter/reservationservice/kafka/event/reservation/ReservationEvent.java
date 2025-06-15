package com.appcenter.reservationservice.kafka.event.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationEvent {
    private LocalDateTime createdAt;
}
