package com.a2.ticketservice.service;

import com.a2.ticketservice.client.flightservice.FlightDto;
import com.a2.ticketservice.client.userservice.DiscountDto;
import com.a2.ticketservice.domain.Ticket;
import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.repository.TicketRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class TicketServiceImpl {
     private TicketRepository ticketRepository;
     private RestTemplate flightServiceRestTemplate;
     private RestTemplate userServiceRestTemplate;

    public TicketServiceImpl(TicketRepository ticketRepository, RestTemplate flightServiceRestTemplate, RestTemplate userServiceRestTemplate) {
        this.ticketRepository = ticketRepository;
        this.flightServiceRestTemplate = flightServiceRestTemplate;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    public void createTicket(TicketCreateDto ticketCreateDto){
        //Get flight from flight service
        ResponseEntity<FlightDto> flightDtoResponseEntity = flightServiceRestTemplate.exchange("/flight/"+ticketCreateDto.getFlightId(),
                HttpMethod.GET, null, FlightDto.class);
        //Get discount from user service
        ResponseEntity<DiscountDto> discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/"+ticketCreateDto.getUserId()+"/discount",
                HttpMethod.GET, null, DiscountDto.class);
        //Post ticket to user database, update miles

        BigDecimal price = BigDecimal.ONE;
        Ticket ticket = new Ticket(ticketCreateDto.getUserId(), ticketCreateDto.getFlightId(), price);
        ticketRepository.save(ticket);
    }
}
