package com.appcenter.queryserivce.domain;

import com.appcenter.event.reservation.ReservationCancelledEvent;
import com.appcenter.event.reservation.ReservationCreatedEvent;
import com.appcenter.event.stock.StockDecreasedEvent;
import com.appcenter.event.stock.StockFailedEvent;
import com.appcenter.event.ticket.TicketFailedEvent;
import com.appcenter.event.ticket.TicketIssuedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_details")
@Getter
@NoArgsConstructor
public class ReservationDetails implements Serializable {

    @Id
    private Long reservationId;

    // User Information
    private String username;

    // Ticket Information
    private Long ticketId;
    private String ticketName;
    private String category;

    // Stock Information
    private Integer remainingStock;

    // Status Information
    private String reservationStatus;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime cancelledAt;

    public void apply(ReservationCreatedEvent event) {
        this.reservationId = event.getReservationId();
        this.username = event.getUsername();
        this.ticketId = event.getTicketId();
        this.reservationStatus = "PENDING";
        this.createdAt = event.getCreatedAt();
    }

    public void apply(TicketIssuedEvent event) {
        this.ticketName = event.getTicketName();
        this.category = event.getCategory();
        this.reservationStatus = "TICKET_ISSUED";
        this.confirmedAt = event.getIssuedAt();
    }

    public void apply(StockDecreasedEvent event) {
        this.remainingStock = event.getRemainingQuantity();
        this.reservationStatus = "CONFIRMED";
    }

    public void apply(StockFailedEvent event) {
        this.failureReason = event.getReason();
        this.reservationStatus = "FAILED";
    }

    public void apply(TicketFailedEvent event) {
        this.reservationStatus = "CANCELLED";
        this.failureReason = event.getReason();
        this.cancelledAt = LocalDateTime.now();
    }

    public void apply(ReservationCancelledEvent event) {
        this.reservationStatus = "CANCELLED";
        this.failureReason = event.getReason();
        this.cancelledAt = event.getCancelledAt();
    }

}
