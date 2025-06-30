package com.appcenter.ticketservice.kafka.event.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationEvent {
    private LocalDateTime createdAt;
}
