package com.a2.ticketservice.mapper;

import com.a2.ticketservice.domain.Ticket;
import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.dto.TicketDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketDto ticketToTicketDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setUserId(ticket.getUserId());
        ticketDto.setFlightId(ticket.getFlightId());
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setTicketStatus(ticket.getTicketStatus());
        return ticketDto;
    }
}
