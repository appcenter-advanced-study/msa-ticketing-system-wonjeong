package com.appcenter.ticketservice.kafka;

import com.appcenter.ticketservice.kafka.event.reservation.ReservationCreatedEvent;
import com.appcenter.ticketservice.kafka.event.ticket.TicketFailedEvent;
import com.appcenter.ticketservice.kafka.event.ticket.TicketIssuedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketEventListener {
    private final TicketEventPublisher ticketEventPublisher;

    @KafkaListener(
            topics = "reservation.created",
            groupId = "ticket-group",
            containerFactory = "reservationKafkaListenerContainerFactory"
    )
    public void handleReservationCreated(ReservationCreatedEvent event) {
        log.info("예약 생성 이벤트 수신: {}", event);

        boolean issueResult = simulateTicketIssue();

        if (issueResult) {
            log.info("티켓 발급 성공");
            TicketIssuedEvent issuedEvent = TicketIssuedEvent.builder()
                    .reservationId(event.getReservationId())
                    .ticketId(event.getTicketId())
                    .build();
            ticketEventPublisher.publishTicketIssued(issuedEvent);
        } else {
            log.warn("티켓 발급 실패");
            TicketFailedEvent failedEvent = TicketFailedEvent.builder()
                    .reservationId(event.getReservationId())
                    .ticketId(event.getTicketId())
                    .reason("티켓 발급 문제로 인해 티켓 발급 실패!")
                    .build();
            ticketEventPublisher.publishTicketFailed(failedEvent);
        }
    }

    private boolean simulateTicketIssue() {
        return Math.random() < 0.7;
    }
}
