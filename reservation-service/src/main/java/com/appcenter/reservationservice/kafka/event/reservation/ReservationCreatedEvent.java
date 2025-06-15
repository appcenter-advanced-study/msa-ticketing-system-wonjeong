package com.appcenter.reservationservice.kafka.event.reservation;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReservationCreatedEvent extends ReservationEvent{
    private Long reservationId;
    private Long ticketId;
    private String username;
}
