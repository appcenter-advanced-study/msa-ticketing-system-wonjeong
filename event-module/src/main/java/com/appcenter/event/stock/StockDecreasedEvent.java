package com.appcenter.event.stock;

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
