package com.a2.ticketservice.service;

import com.a2.ticketservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    void createTicket(TicketCreateDto ticketCreateDto);

    SoldTicketsDto flightCapacity(Long flightId);

    List<TicketDto> findAllByUserId(Long userId);

    void cancelTickets(FlightCancelDto flightCancelDto);
}
