package com.a2.ticketservice.service;

import com.a2.ticketservice.dto.CancelTicketDto;
import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.dto.TicketDto;
import org.springframework.data.domain.Page;

public interface TicketService {

    void createTicket(TicketCreateDto ticketCreateDto);

    Integer flightCapacity(Long flightId);

    Page<TicketDto> findAllByUserId(Long userId);

    void cancelTickets(CancelTicketDto cancelTicketDto);
}
