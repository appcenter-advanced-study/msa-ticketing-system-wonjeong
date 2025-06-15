package com.appcenter.stockservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDecreasedEvent extends StockEvent {
    private Long reservationId;
    private Long ticketId;
}
