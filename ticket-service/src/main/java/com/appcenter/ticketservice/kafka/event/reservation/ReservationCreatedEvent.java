package com.appcenter.ticketservice.kafka.event.reservation;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCreatedEvent extends ReservationEvent {
    private Long reservationId;
    private Long ticketId;
    private String username;
}
