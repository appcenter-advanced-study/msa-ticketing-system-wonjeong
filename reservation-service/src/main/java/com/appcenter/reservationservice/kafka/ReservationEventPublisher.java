package com.appcenter.reservationservice.kafka;

import com.appcenter.event.reservation.ReservationCancelledEvent;
import com.appcenter.event.reservation.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishReservationCreated(ReservationCreatedEvent event) {
        kafkaTemplate.send("reservation.created", event);
    }

    public void publishReservationCancelled(ReservationCancelledEvent event) {
        kafkaTemplate.send("reservation.cancel", event);
    }
}
