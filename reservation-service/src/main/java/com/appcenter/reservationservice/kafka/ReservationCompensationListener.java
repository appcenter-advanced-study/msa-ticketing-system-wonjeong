package com.appcenter.reservationservice.kafka;

import com.appcenter.reservationservice.kafka.event.ticket.TicketFailedEvent;
import com.appcenter.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCompensationListener {

    private final ReservationService reservationService;

    @KafkaListener(
            topics = "ticket.issue.failed",
            groupId = "reservation-compensation-group",
            containerFactory = "ticketFailedKafkaListenerContainerFactory"
    )
    public void handleTicketIssueFailed(TicketFailedEvent event) {
        log.info("티켓 발급 실패 수신 -> 예약 취소: {}", event);
        reservationService.cancelReservation(event.getReservationId());
    }
}
