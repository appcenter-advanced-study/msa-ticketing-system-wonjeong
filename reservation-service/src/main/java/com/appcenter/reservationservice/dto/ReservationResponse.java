package com.appcenter.reservationservice.dto;


public record ReservationResponse(
        Long id,
        String username,
        TicketResponse ticket
) {
}
