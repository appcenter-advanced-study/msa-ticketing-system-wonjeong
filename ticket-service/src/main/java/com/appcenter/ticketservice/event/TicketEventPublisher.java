package com.appcenter.ticketservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventPublisher {
    private final KafkaTemplate<String, TicketEvent> kafkaTemplate;

    public void publishTicketIssued(TicketIssuedEvent event) {
        kafkaTemplate.send("ticket.issued", event);
    }

    public void publishTicketFailed(TicketFailedEvent event) {
        kafkaTemplate.send("ticket.issue.failed", event);
    }
}
