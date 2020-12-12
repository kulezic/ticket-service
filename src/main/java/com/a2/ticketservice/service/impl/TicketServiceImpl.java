package com.a2.ticketservice.service.impl;

import com.a2.ticketservice.client.flightservice.FlightDto;
import com.a2.ticketservice.client.userservice.DiscountDto;
import com.a2.ticketservice.domain.Ticket;
import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.exception.NotFoundException;
import com.a2.ticketservice.repository.TicketRepository;
import com.a2.ticketservice.service.TicketService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class TicketServiceImpl implements TicketService {
     private TicketRepository ticketRepository;
     private RestTemplate flightServiceRestTemplate;
     private RestTemplate userServiceRestTemplate;

    public TicketServiceImpl(TicketRepository ticketRepository, RestTemplate flightServiceRestTemplate, RestTemplate userServiceRestTemplate) {
        this.ticketRepository = ticketRepository;
        this.flightServiceRestTemplate = flightServiceRestTemplate;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    public void createTicket(TicketCreateDto ticketCreateDto){

        ResponseEntity<FlightDto> flightDtoResponseEntity = null;
        try {
            //Get flight from flight service
            flightDtoResponseEntity = flightServiceRestTemplate.exchange("/flight/"+ticketCreateDto.getFlightId(),
                    HttpMethod.GET, null, FlightDto.class);

        }catch (HttpClientErrorException e){
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("Flight with id: %d not found.", ticketCreateDto.getFlightId()));
        }catch (Exception e){
            e.printStackTrace();
        }
        ResponseEntity<DiscountDto> discountDtoResponseEntity = null;

        try{
            //Get discount from user service
            discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/"+ticketCreateDto.getUserId()+"/discount",
                    HttpMethod.GET, null, DiscountDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        //TODO Post ticket to user database, update miles, update rank

        //Calculate price
        BigDecimal price = flightDtoResponseEntity.getBody().getPrice().multiply(discountDtoResponseEntity.getBody().getDiscount());

        //Save ticket
        Ticket ticket = new Ticket(ticketCreateDto.getUserId(), ticketCreateDto.getFlightId(), price);
        ticketRepository.save(ticket);
    }
}
