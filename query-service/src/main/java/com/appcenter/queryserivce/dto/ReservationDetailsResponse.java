package com.appcenter.queryserivce.dto;

import com.appcenter.queryserivce.domain.ReservationDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailsResponse {
    private Long reservationId;

    private String username;

    private Long ticketId;
    private String ticketName;
    private String category;

    private Integer remainingStock;

    private String reservationStatus;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime cancelledAt;

    public static ReservationDetailsResponse from(ReservationDetails reservationDetails) {
        return ReservationDetailsResponse.builder()
                .reservationId(reservationDetails.getReservationId())
                .username(reservationDetails.getUsername())
                .ticketId(reservationDetails.getTicketId())
                .ticketName(reservationDetails.getTicketName())
                .category(reservationDetails.getCategory())
                .remainingStock(reservationDetails.getRemainingStock())
                .reservationStatus(reservationDetails.getReservationStatus())
                .failureReason(reservationDetails.getFailureReason())
                .createdAt(reservationDetails.getCreatedAt())
                .confirmedAt(reservationDetails.getConfirmedAt())
                .cancelledAt(reservationDetails.getCancelledAt())
                .build();
    }
}
