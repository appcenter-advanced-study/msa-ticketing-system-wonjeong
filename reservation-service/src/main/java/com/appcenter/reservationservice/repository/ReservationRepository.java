package com.appcenter.reservationservice.repository;

import com.appcenter.reservationservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
