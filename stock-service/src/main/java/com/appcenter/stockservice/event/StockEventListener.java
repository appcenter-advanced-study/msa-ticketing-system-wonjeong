package com.appcenter.stockservice.event;

import com.appcenter.stockservice.service.StockService;
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

    @KafkaListener(topics = "reservation.created", groupId = "stock-group")
    public void handleReservationCreated(ReservationCreatedEvent event) {
        log.info("예약 생성 이벤트 수신: {}", event);

        boolean simulate = this.simulateTicketIssue();

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
                    .reason("티켓 발급 실패")
                    .build();
            stockEventPublisher.publishStockFailed(failedEvent);
        }

    }

    private boolean simulateTicketIssue() {
        return Math.random() < 0.7;
    }
}
