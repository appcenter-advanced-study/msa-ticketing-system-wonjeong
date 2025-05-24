package com.appcenter.ticketservice.controller;

import com.appcenter.ticketservice.dto.TicketResponse;
import com.appcenter.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Void> createTicket(@RequestParam String name) {
        ticketService.createTicket(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketResponse>> getAllTicket() {
        List<TicketResponse> result = ticketService.findAll();
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<TicketResponse> updateTicket(@RequestParam Long id, @RequestParam String name) {
        TicketResponse result = ticketService.updateTicket(id, name);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTicket(@RequestParam Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long ticketId) {
        TicketResponse result = ticketService.findById(ticketId);
        return ResponseEntity.ok(result);
    }

}
