package com.a2.ticketservice.service;

import com.a2.ticketservice.dto.TicketCreateDto;

public interface TicketService {

    void createTicket(TicketCreateDto ticketCreateDto);
}
