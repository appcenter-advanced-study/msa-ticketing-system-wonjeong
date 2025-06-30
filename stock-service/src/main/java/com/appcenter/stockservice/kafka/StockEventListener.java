package com.appcenter.stockservice.kafka;

import com.appcenter.stockservice.kafka.event.stock.StockDecreasedEvent;
import com.appcenter.stockservice.kafka.event.stock.StockFailedEvent;
import com.appcenter.stockservice.kafka.event.ticket.TicketIssuedEvent;
import com.appcenter.stockservice.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventListener {
    private final StockService stockService;
    private final StockEventPublisher stockEventPublisher;

    @KafkaListener(topics = "ticket.issued", groupId = "stock-group")
    public void handleTicketIssued(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // LocalDateTime 직렬화/역직렬화를 위한 모듈 등록
            objectMapper.registerModule(new JavaTimeModule());
            TicketIssuedEvent event = objectMapper.readValue(message, TicketIssuedEvent.class);

            log.info("티켓 발급 이벤트 수신: {}", event);

            boolean simulate = this.simulateDecreaseStock();

            if (simulate) {
                try {
                    stockService.decreaseStock(event.getReservationId());
                    StockDecreasedEvent decreasedEvent = StockDecreasedEvent.builder()
                            .reservationId(event.getReservationId())
                            .ticketId(event.getTicketId())
                            .build();
                    stockEventPublisher.publishStockDecreased(decreasedEvent);
                } catch (Exception e) {
                    StockFailedEvent failedEvent = StockFailedEvent.builder()
                            .reservationId(event.getReservationId())
                            .ticketId(event.getTicketId())
                            .reason(e.getMessage())
                            .build();
                    stockEventPublisher.publishStockFailed(failedEvent);
                }
            } else {
                StockFailedEvent failedEvent = StockFailedEvent.builder()
                        .reservationId(event.getReservationId())
                        .ticketId(event.getTicketId())
                        .reason("재고 차감 실패")
                        .build();
                stockEventPublisher.publishStockFailed(failedEvent);
            }

        } catch (Exception e) {
            log.error("메시지 변환 오류: {}", e.getMessage(), e);
        }

    }

    private boolean simulateDecreaseStock() {
        return Math.random() > 0.1;
    }
}
