package com.appcenter.reservationservice.kafka.event.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StockEvent {
    private LocalDateTime createdAt;
}
