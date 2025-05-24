package com.appcenter.stockservice.service;

import com.appcenter.stockservice.client.TicketClient;
import com.appcenter.stockservice.domain.Stock;
import com.appcenter.stockservice.dto.StockResponse;
import com.appcenter.stockservice.dto.TicketResponse;
import com.appcenter.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final TicketClient ticketClient;

    public Integer findQuantityByTicketId(Long ticketStockId) {
        Stock stock = stockRepository.findById(ticketStockId).orElseThrow();
        return stock.getQuantity();
    }

    public void setStockQuantity(Long ticketId, Integer quantity) {
        Stock stock = new Stock(quantity, ticketId);
        stockRepository.save(stock);
    }

    public StockResponse updateStockQuantity(Long ticketId, Integer quantity) {
        Stock stock = stockRepository.findById(ticketId).orElseThrow();
        stock.updateQuantity(quantity);
        Stock savedTicketStock = stockRepository.save(stock);
        TicketResponse ticketResponse = ticketClient.getTicket(ticketId);
        return new StockResponse(savedTicketStock.getId(), savedTicketStock.getQuantity(), ticketResponse);
    }

    @Transactional
    public void decreaseStock(Long ticketId) {
        Stock stock = stockRepository.findByTicketId(ticketId);
        stock.decreaseQuantity();
        stockRepository.save(stock);
    }

    @Transactional
    public void increaseStock(Long ticketId) {
        Stock stock = stockRepository.findByTicketId(ticketId);
        stock.increaseQuantity();
        stockRepository.save(stock);
    }
}
