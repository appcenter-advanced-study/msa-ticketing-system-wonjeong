package com.appcenter.stockservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventPublisher {
    private final KafkaTemplate<String, StockEvent> kafkaTemplate;

    public void publishStockDecreased(StockDecreasedEvent event) {
        kafkaTemplate.send("stock.decreased", event);
    }

    public void publishStockFailed(StockFailedEvent event) {
        kafkaTemplate.send("stock.failed", event);
    }
}
