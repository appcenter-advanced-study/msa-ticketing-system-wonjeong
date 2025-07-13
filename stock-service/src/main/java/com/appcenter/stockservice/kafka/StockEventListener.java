package com.appcenter.stockservice.kafka;

import com.appcenter.event.stock.StockDecreasedEvent;
import com.appcenter.event.stock.StockFailedEvent;
import com.appcenter.event.ticket.TicketIssuedEvent;
import com.appcenter.stockservice.domain.Stock;
import com.appcenter.stockservice.repository.StockRepository;
import com.appcenter.stockservice.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventListener {
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final StockEventPublisher stockEventPublisher;

    @Transactional
    @KafkaListener(topics = "ticket.issued", groupId = "stock-group")
    public void handleTicketIssued(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // LocalDateTime 직렬화/역직렬화를 위한 모듈 등록
            objectMapper.registerModule(new JavaTimeModule());
            TicketIssuedEvent event = objectMapper.readValue(message, TicketIssuedEvent.class);

            log.info("티켓 발급 이벤트 수신: {}", event);

            boolean simulate = this.simulateDecreaseStock();
            log.info("시뮬레이트 값 : " + simulate);
            if (simulate) {
                stockService.decreaseStock(event.getTicketId());
                Stock stock = stockRepository.findByTicketId(event.getTicketId());
                StockDecreasedEvent decreasedEvent = StockDecreasedEvent.builder()
                        .reservationId(event.getReservationId())
                        .ticketId(event.getTicketId())
                        .remainingQuantity(stock.getQuantity())
                        .decreasedAt(LocalDateTime.now())
                        .build();
                stockEventPublisher.publishStockDecreased(decreasedEvent);
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
        final double SUCCESS_RATE = 0.9; // 성공 확률 90%
        return Math.random() < SUCCESS_RATE;
    }
}
