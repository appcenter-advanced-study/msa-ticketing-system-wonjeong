package com.appcenter.stockservice.dto;


public record StockResponse(
        Long id,
        Integer quantity,
        TicketResponse ticket
) {
}
