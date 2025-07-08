package com.appcenter.event.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDecreasedEvent extends StockEvent {
    private Long reservationId;
    private Long ticketId;
    private Integer remainingQuantity;
    private LocalDateTime decreasedAt;
}
