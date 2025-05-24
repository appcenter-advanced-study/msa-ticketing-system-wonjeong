package com.appcenter.reservationservice.event;

import com.appcenter.reservationservice.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final ReservationService reservationService;

    @KafkaListener(topics = "reservation-event", groupId = "ticketing-group")
    @Transactional
    public void listenReservationEvent(ReservationEvent reservationEvent) {
        log.info("예약 이벤트 수신: {}", reservationEvent);

        reservationService.createReservation(reservationEvent.getUsername(), reservationEvent.getTicketId());
    }
}
