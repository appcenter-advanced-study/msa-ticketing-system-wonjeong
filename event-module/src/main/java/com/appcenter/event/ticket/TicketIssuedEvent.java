package com.appcenter.event.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketIssuedEvent extends TicketEvent{
    private Long reservationId;
    private Long ticketId;
}
