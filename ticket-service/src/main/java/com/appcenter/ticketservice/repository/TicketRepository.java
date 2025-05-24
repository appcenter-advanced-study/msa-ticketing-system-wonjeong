package com.appcenter.ticketservice.repository;

import com.appcenter.ticketservice.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
