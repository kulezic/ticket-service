package com.a2.ticketservice.service;

import com.a2.ticketservice.client.flightservice.FlightCancelDto;
import com.a2.ticketservice.dto.*;
import org.springframework.data.domain.Page;

public interface TicketService {

    void createTicket(TicketCreateDto ticketCreateDto);

    SoldTicketsDto flightCapacity(Long flightId);

    Page<TicketDto> findAllByUserId(Long userId);

    void cancelTickets(FlightCancelDto flightCancelDto);
}
