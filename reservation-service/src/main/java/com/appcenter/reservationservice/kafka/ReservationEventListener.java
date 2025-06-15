package com.appcenter.reservationservice.kafka;

import com.appcenter.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final ReservationService reservationService;

    @KafkaListener(topics = "reservation.cancel", groupId = "reservation-cancel-group")
    public void listenReservationCancel(Long reservationId) {
        reservationService.cancelReservation(reservationId);
    }
}
