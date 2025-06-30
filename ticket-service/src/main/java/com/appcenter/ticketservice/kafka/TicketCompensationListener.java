package com.appcenter.ticketservice.kafka;

import com.appcenter.ticketservice.kafka.event.stock.StockFailedEvent;
import com.appcenter.ticketservice.kafka.event.ticket.TicketFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketCompensationListener {
    private final TicketEventPublisher ticketEventPublisher;

    @KafkaListener(
            topics = "stock.failed",
            groupId ="ticket-compansation-group",
            containerFactory = "stockFailedKafkaListenerContainerFactory"
    )
    public void handleStockFailed(StockFailedEvent event) {
        log.info("재고 감소 실패 수신 -> 티켓 발급 취소 : {}", event);
        TicketFailedEvent failedEvent = TicketFailedEvent.builder()
                .reservationId(event.getReservationId())
                .ticketId(event.getTicketId())
                .reason("재고 감소 실패로 인해 티켓 발급 실패!")
                .build();
        ticketEventPublisher.publishTicketFailed(failedEvent);
    }
}
