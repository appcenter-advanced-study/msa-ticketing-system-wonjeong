package com.appcenter.event.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReservationCancelledEvent extends ReservationEvent {
    private Long reservationId;
    private String reason;
    private LocalDateTime cancelledAt;
}
