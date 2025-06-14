package com.appcenter.reservationservice.event;

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
