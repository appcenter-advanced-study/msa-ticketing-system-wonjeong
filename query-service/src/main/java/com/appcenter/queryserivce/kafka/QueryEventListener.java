package com.appcenter.queryserivce.kafka;

import com.appcenter.event.reservation.ReservationCancelledEvent;
import com.appcenter.event.reservation.ReservationCreatedEvent;
import com.appcenter.event.stock.StockDecreasedEvent;
import com.appcenter.event.stock.StockFailedEvent;
import com.appcenter.event.ticket.TicketFailedEvent;
import com.appcenter.event.ticket.TicketIssuedEvent;
import com.appcenter.queryserivce.domain.ReservationDetails;
import com.appcenter.queryserivce.repository.ReservationDetailRepository;
import com.appcenter.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryEventListener {
    private final ReservationDetailRepository repository;

    @KafkaListener(topics = "reservation.created", groupId = "query-group")
    public void handleReservationCreated(String message) {
        ReservationCreatedEvent event = EventUtil.mappingMessageToClass(message, ReservationCreatedEvent.class);
        log.info("수신 reservation.created : {}", event);

        if (event != null) {
            ReservationDetails details = new ReservationDetails();
            details.apply(event);
            repository.save(details);
        }
    }

    @KafkaListener(topics = "ticket.issued", groupId = "query-group")
    public void handleTicketIssued(String message) {
        TicketIssuedEvent event = EventUtil.mappingMessageToClass(message, TicketIssuedEvent.class);
        log.info("수신 ticket.issued : {}", event);

        if (event != null) {
            repository.findById((event.getReservationId()))
                    .ifPresent(details -> {
                        details.apply(event);
                        repository.save(details);
                    });
        }
    }

    @KafkaListener(topics = "stock.decreased", groupId = "query-group")
    public void handleStockDecreased(String message) {
        StockDecreasedEvent event = EventUtil.mappingMessageToClass(message, StockDecreasedEvent.class);
        log.info("수신 stock.decreased: {}", event);

        if (event != null) {
            repository.findById(event.getReservationId())
                    .ifPresent(details -> {
                        details.apply(event);
                        repository.save(details);
                    });
        }
    }

    @KafkaListener(topics = "ticket.issue.failed", groupId = "query-group")
    public void handleTicketFailed(String message) {
        TicketFailedEvent event = EventUtil.mappingMessageToClass(message, TicketFailedEvent.class);
        log.warn("수신 (ticket.issue.failed): {}", event);

        if (event != null) {
            repository.findById(event.getReservationId()).ifPresent(details -> {
                details.apply(event);
                repository.save(details);
            });
        }
    }

    @KafkaListener(topics = "stock.failed", groupId = "query-group")
    public void handleStockFailed(String message) {
        StockFailedEvent event = EventUtil.mappingMessageToClass(message, StockFailedEvent.class);
        log.warn("수신 (stock.failed): {}", event);

        if (event != null) {
            repository.findById(event.getReservationId()).ifPresent(details -> {
                details.apply(event);
                repository.save(details);
            });
        }
    }

    @KafkaListener(topics = "reservation.cancel", groupId = "query-group")
    public void handleReservationCancelled(String message) {
        ReservationCancelledEvent event = EventUtil.mappingMessageToClass(message, ReservationCancelledEvent.class);
        log.info("수신 (reservation.cancel): {}", event);

        if (event != null) {
            repository.findById(event.getReservationId()).ifPresent(details -> {
                details.apply(event);
                repository.save(details);
            });
        }
    }
}
