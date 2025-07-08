package com.appcenter.queryserivce.service;

import com.appcenter.queryserivce.domain.ReservationDetails;
import com.appcenter.queryserivce.dto.ReservationDetailsResponse;
import com.appcenter.queryserivce.repository.ReservationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final ReservationDetailRepository repository;

    public ReservationDetailsResponse findById(Long reservationId) {
        ReservationDetails entity = repository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("ResponseDetail이 존재하지 않습니다."));
        return ReservationDetailsResponse.from(entity);
    }
}
