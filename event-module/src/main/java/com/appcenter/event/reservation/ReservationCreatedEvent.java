package com.appcenter.event.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReservationCreatedEvent extends ReservationEvent{
    private Long reservationId;
    private Long ticketId;
    private String username;
    private LocalDateTime createdAt;
}
