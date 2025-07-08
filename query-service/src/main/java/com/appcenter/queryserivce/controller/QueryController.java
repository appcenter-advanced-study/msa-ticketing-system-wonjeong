package com.appcenter.queryserivce.controller;

import com.appcenter.queryserivce.dto.ReservationDetailsResponse;
import com.appcenter.queryserivce.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/queries")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationDetailsResponse> getReservationDetailsById(@PathVariable Long reservationId) {
        ReservationDetailsResponse result = queryService.findById(reservationId);
        return ResponseEntity.ok(result);
    }
}
