package com.appcenter.reservationservice.repository;

import com.appcenter.reservationservice.domain.ReservationOutBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationOutBoxRepository extends JpaRepository<ReservationOutBox, Long> {
}
