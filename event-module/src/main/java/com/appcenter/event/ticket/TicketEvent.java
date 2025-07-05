package com.appcenter.event.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TicketEvent {
    private LocalDateTime createdAt;
}
