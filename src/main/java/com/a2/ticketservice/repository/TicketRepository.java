package com.a2.ticketservice.repository;

import com.a2.ticketservice.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByUserIdAndFlightId(Long userId, Long flightId);
}
