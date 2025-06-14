package com.appcenter.reservationservice.event;

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

    @KafkaListener(topics = "ticket.issue.failed", groupId = "reservation-compensation-group")
    public void handleTicketIssueFailed(Long reservationId) {
        log.info("티켓 발급 실패 수신 -> 예약 취소: reservationId={}", reservationId);
        reservationService.cancelReservation(reservationId);
    }
}
