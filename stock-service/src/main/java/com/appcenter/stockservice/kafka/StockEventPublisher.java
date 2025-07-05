package com.appcenter.stockservice.kafka;

import com.appcenter.event.stock.StockDecreasedEvent;
import com.appcenter.event.stock.StockFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishStockDecreased(StockDecreasedEvent event) {
        kafkaTemplate.send("stock.decreased", event);
    }

    public void publishStockFailed(StockFailedEvent event) {
        kafkaTemplate.send("stock.failed", event);
    }
}
