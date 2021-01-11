package com.a2.ticketservice.service.impl;

import com.a2.ticketservice.client.flightservice.FlightCancelDto;
import com.a2.ticketservice.client.flightservice.FlightCapacityDto;
import com.a2.ticketservice.client.flightservice.FlightDto;
import com.a2.ticketservice.client.userservice.DiscountDto;
import com.a2.ticketservice.domain.Ticket;
import com.a2.ticketservice.dto.*;
import com.a2.ticketservice.exception.CapacityFullException;
import com.a2.ticketservice.exception.NotFoundException;
import com.a2.ticketservice.mapper.TicketMapper;
import com.a2.ticketservice.repository.TicketRepository;
import com.a2.ticketservice.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
     private TicketRepository ticketRepository;

     private RestTemplate flightServiceRestTemplate;
     private RestTemplate userServiceRestTemplate;

     private TicketMapper ticketMapper;
     private ObjectMapper objectMapper;

     private JmsTemplate jmsTemplate;
     private String destinationCancelTicket;
     private String destinationIncrementMiles;


    public TicketServiceImpl(TicketRepository ticketRepository,
                             RestTemplate flightServiceRestTemplate,
                             RestTemplate userServiceRestTemplate,
                             TicketMapper ticketMapper,
                             ObjectMapper objectMapper,
                             JmsTemplate jmsTemplate,
                             @Value("${destination.cancel-ticket}") String destinationCancelTicket,
                             @Value("${destination.increment-miles}") String destinationIncrementMiles) {
        this.ticketRepository = ticketRepository;
        this.flightServiceRestTemplate = flightServiceRestTemplate;
        this.userServiceRestTemplate = userServiceRestTemplate;
         this.ticketMapper = ticketMapper;
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
        this.destinationCancelTicket = destinationCancelTicket;
        this.destinationIncrementMiles = destinationIncrementMiles;
    }

    public void createTicket(TicketCreateDto ticketCreateDto){

        //TODO add card

        //Get flight from flight service
        ResponseEntity<FlightDto> flightDtoResponseEntity = null;
        try {
            flightDtoResponseEntity = flightServiceRestTemplate.exchange("/flight/"+ticketCreateDto.getFlightId(),
                    HttpMethod.GET, null, FlightDto.class);

        }catch (HttpClientErrorException e){
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("Flight with id: %d not found.", ticketCreateDto.getFlightId()));
        }catch (Exception e){
            e.printStackTrace();
        }

        //Get discount from user service
        ResponseEntity<DiscountDto> discountDtoResponseEntity = null;
        try{
            discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/"+ticketCreateDto.getUserId()+"/discount",
                    HttpMethod.GET, null, DiscountDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Check capacity
        ResponseEntity<FlightCapacityDto> flightCapacityDtoResponseEntity = null;
        try{

            flightCapacityDtoResponseEntity = flightServiceRestTemplate.exchange("/capacity/" + ticketCreateDto.getFlightId(),
                    HttpMethod.GET, null, FlightCapacityDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(flightCapacityDtoResponseEntity.getBody().getCapacity() <= flightCapacity(ticketCreateDto.getFlightId()).getSoldTickets())
            throw new CapacityFullException("Flight capacity is full");

        //Calculate price
        BigDecimal price = flightDtoResponseEntity.getBody().getPrice().multiply(discountDtoResponseEntity.getBody().getDiscount());

        //Update miles
        try {
            jmsTemplate.convertAndSend(destinationIncrementMiles, objectMapper.writeValueAsString(
                    new IncrementMilesDto(ticketCreateDto.getUserId(), flightDtoResponseEntity.getBody().getMiles())
            ));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //Save ticket
        Ticket ticket = new Ticket(ticketCreateDto.getUserId(), ticketCreateDto.getFlightId(), price);
        ticketRepository.save(ticket);
    }

    @Override
    public SoldTicketsDto flightCapacity(Long flightId) {
        List<Ticket> tickets = ticketRepository.findAllByFlightId(flightId);
        if (tickets != null) return new SoldTicketsDto(tickets.size());
        return new SoldTicketsDto(0);
    }

    @Override
    public List<TicketDto> findAllByUserId(Long userId) {
        return (List<TicketDto>) ticketRepository.findAllByUserId(userId).stream().map(ticketMapper::ticketToTicketDto);
    }

    @Override
    public void cancelTickets(FlightCancelDto flightCancelDto){
        List<Ticket> tickets = ticketRepository.findAllByFlightId(flightCancelDto.getFlightId());
        for (Ticket ticket:
             tickets) {
            ticket.setTicketStatus("CANCELED");
            ticketRepository.save(ticket);
            try {
                jmsTemplate.convertAndSend(destinationCancelTicket,
                        objectMapper.writeValueAsString(new TicketCancelDto(ticket.getUserId(), flightCancelDto.getMiles())));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
