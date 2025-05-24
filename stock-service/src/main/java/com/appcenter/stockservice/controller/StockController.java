package com.appcenter.stockservice.controller;

import com.appcenter.stockservice.dto.StockResponse;
import com.appcenter.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Void> setStockQuantity(@RequestParam Long ticketId, @RequestParam Integer quantity) {
        stockService.setStockQuantity(ticketId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> getStockQuantity(@RequestParam Long ticketStockId) {
        Integer result = stockService.findQuantityByTicketId(ticketStockId);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<StockResponse> updateStockQuantity(@RequestParam Long ticketStockId, @RequestParam int quantity) {
        StockResponse result = stockService.updateStockQuantity(ticketStockId, quantity);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{ticketId}/decrease")
    public ResponseEntity<Void> decreaseStock(@PathVariable Long ticketId) {
        stockService.decreaseStock(ticketId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ticketId}/increase")
    public ResponseEntity<Void> increaseStock(@PathVariable Long ticketId) {
        stockService.increaseStock(ticketId);
        return ResponseEntity.ok().build();
    }


}
