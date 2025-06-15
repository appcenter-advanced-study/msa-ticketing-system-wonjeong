package com.appcenter.reservationservice.outbox;

import com.appcenter.reservationservice.domain.ReservationOutBox;
import com.appcenter.reservationservice.kafka.event.reservation.ReservationCancelledEvent;
import com.appcenter.reservationservice.kafka.event.reservation.ReservationCreatedEvent;
import com.appcenter.reservationservice.kafka.ReservationEventPublisher;
import com.appcenter.reservationservice.repository.ReservationOutBoxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationOutboxPublisher {
    private final ReservationOutBoxRepository reservationOutBoxRepository;
    private final ReservationEventPublisher reservationEventPublisher;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000) // 1초마다 주기적으로 폴링
    public void publishOutboxEvents() {
        List<ReservationOutBox> outBoxList = reservationOutBoxRepository.findAll();

        for (ReservationOutBox outbox : outBoxList) {
            try {
                if ("RESERVATION_CREATED".equalsIgnoreCase(outbox.getEventType())) {
                    // 직렬화된 payload -> 이벤트 객체로 역직렬화
                    ReservationCreatedEvent event = objectMapper.readValue(outbox.getPayload(), ReservationCreatedEvent.class);
                    reservationEventPublisher.publishReservationCreated(event);
                    log.info("reservation.created 발행: {}", event);
                } else if ("RESERVATION_CANCELLED".equalsIgnoreCase(outbox.getEventType())) {
                    ReservationCancelledEvent event = objectMapper.readValue(outbox.getPayload(), ReservationCancelledEvent.class);
                    reservationEventPublisher.publishReservationCancelled(event);
                    log.info("reservation.cancelled 발행: {}", event);
                }
                // 성공시 Outbox 삭제
                reservationOutBoxRepository.delete(outbox);
            } catch (Exception e) {
                log.error("OutBox 발행 실패: {}", e.getMessage(), e);
            }
        }
    }
}
