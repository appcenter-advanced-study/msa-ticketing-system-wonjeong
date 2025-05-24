package com.appcenter.stockservice.client;

import com.appcenter.stockservice.dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ticket-service")
public interface TicketClient {

    @GetMapping("/api/v1/tickets/{ticketId}")
    TicketResponse getTicket(@PathVariable Long ticketId);
}
