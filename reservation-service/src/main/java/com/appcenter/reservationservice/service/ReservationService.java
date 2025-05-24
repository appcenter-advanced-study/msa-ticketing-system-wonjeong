package com.appcenter.reservationservice.service;

import com.appcenter.reservationservice.client.StockClient;
import com.appcenter.reservationservice.client.TicketClient;
import com.appcenter.reservationservice.domain.Reservation;
import com.appcenter.reservationservice.dto.ReservationResponse;
import com.appcenter.reservationservice.dto.TicketResponse;
import com.appcenter.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StockClient stockClient;
    private final TicketClient ticketClient;

    @Transactional
    public void createReservation(String username, Long ticketId) {
        // 재고 감소 요청
        stockClient.decreaseStock(ticketId);

        try {
            Thread.sleep(500); // 락 보유 시간을 인위적으로 증가
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 예약 생성
        Reservation reservation = new Reservation(username, ticketId);
        reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        TicketResponse ticketResponse = ticketClient.getTicket(reservation.getTicketId());
        return new ReservationResponse(reservation.getId(), reservation.getUsername(), ticketResponse);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream().map(reservation -> {
            TicketResponse ticketResponse = ticketClient.getTicket(reservation.getTicketId());
            return new ReservationResponse(reservation.getId(), reservation.getUsername(), ticketResponse);
        }).toList();
    }

//    @Transactional
//    public ReservationResponse updateReservation(Long id, String username, Long ticketId) {
//        // 예약건 조회
//        Reservation foundReservation = reservationRepository.findById(id).orElseThrow();
//        if (ticketId != null) {
//            // 현 티켓 조회
//            Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
//            // 예약건과 원래 관계 되어있었던 티켓정보를 가져옴
//            Ticket oldTicket = foundReservation.getTicket();
//            // 구 티켓 != 현 티켓
//            if (ticket != null && !oldTicket.equals(ticket)) {
//                TicketStock oldTicketStock = ticketStockRepository.findByTicket(oldTicket);
//                TicketStock newTicketStock = ticketStockRepository.findByTicket(ticket);
//                // 원래 티켓 반환 (구 티켓 재고에서 +1을 해줌)
//                oldTicketStock.increaseQuantity();
//                // 새로운 티켓 판매 (새 티켓 재고에서 -1을 해줌)
//                newTicketStock.decreaseQuantity();
//                ticketStockRepository.save(oldTicketStock);
//                ticketStockRepository.save(newTicketStock);
//            }
//
//            // 현 티켓과 관계를 맺는다.
//            foundReservation.updateReservation(username, ticket);
//        }
//
//        foundReservation.updateReservation(username, null);
//        reservationRepository.save(foundReservation);
//
//        return new ReservationResponse(foundReservation.getId(), foundReservation.getUsername(), new TicketResponse(foundReservation.getTicket().getId(), foundReservation.getTicket().getName()));
//    }

    @Transactional
    public void deleteById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        stockClient.increaseStock(reservation.getTicketId());
        reservationRepository.delete(reservation);
    }
}
