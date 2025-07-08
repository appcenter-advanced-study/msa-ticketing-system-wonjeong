package com.appcenter.queryserivce.repository;

import com.appcenter.queryserivce.domain.ReservationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationDetailRepository extends JpaRepository<ReservationDetails, Long> {
    List<ReservationDetails> findByUsername(String username);
}
