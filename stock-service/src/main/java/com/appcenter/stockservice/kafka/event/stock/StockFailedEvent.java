package com.appcenter.stockservice.kafka.event.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockFailedEvent extends StockEvent {
    private Long reservationId;
    private Long ticketId;
    private String reason;
}
