package com.appcenter.stockservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Long ticketId;

    public Stock(Integer quantity, Long ticketId) {
        this.quantity = quantity;
        this.ticketId = ticketId;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity() {
        if (this.quantity <= 0) {
            throw new IllegalStateException("No more tickets available");
        } else {
            this.quantity--;
        }
    }

    public void increaseQuantity() {
        this.quantity++;
    }
}
