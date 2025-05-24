package com.appcenter.reservationservice.controller;


import com.appcenter.reservationservice.dto.ReservationResponse;
import com.appcenter.reservationservice.event.ReservationEvent;
import com.appcenter.reservationservice.event.ReservationEventPublisher;
import com.appcenter.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationEventPublisher reservationEventPublisher;

    @PostMapping
    public ResponseEntity<Void> makeReservation(@RequestParam String username, @RequestParam Long ticketId) {
        ReservationEvent event = ReservationEvent.builder()
                .username(username)
                .ticketId(ticketId)
                .build();
        reservationEventPublisher.publishReservationCreatedEvent(event);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ReservationResponse> getReservation(@RequestParam Long id) {
        ReservationResponse result = reservationService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationResponse>> getAllReservation() {
        List<ReservationResponse> result = reservationService.findAll();
        return ResponseEntity.ok(result);
    }

//    @PutMapping
//    public ResponseEntity<ReservationResponse> updateReservation(@RequestParam Long id, @RequestParam(required = false) String username, @RequestParam(required = false) Long ticketId) {
//        ReservationResponse result = reservationService.updateReservation(id, username, ticketId);
//        return ResponseEntity.ok(result);
//    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(@RequestParam Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
