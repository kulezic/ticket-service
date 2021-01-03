package com.a2.ticketservice.service.impl;

import com.a2.ticketservice.client.flightservice.FlightDto;
import com.a2.ticketservice.client.userservice.DiscountDto;
import com.a2.ticketservice.domain.Ticket;
import com.a2.ticketservice.dto.CancelTicketDto;
import com.a2.ticketservice.dto.CancelMilesDto;
import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.dto.TicketDto;
import com.a2.ticketservice.exception.NotFoundException;
import com.a2.ticketservice.mapper.TicketMapper;
import com.a2.ticketservice.repository.TicketRepository;
import com.a2.ticketservice.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
     private String destinationCancelMiles;


    public TicketServiceImpl(TicketRepository ticketRepository,
                             RestTemplate flightServiceRestTemplate,
                             RestTemplate userServiceRestTemplate,
                             TicketMapper ticketMapper,
                             ObjectMapper objectMapper,
                             JmsTemplate jmsTemplate,
                             @Value("${destination.cancel-miles}") String destinationCancelMiles) {
        this.ticketRepository = ticketRepository;
        this.flightServiceRestTemplate = flightServiceRestTemplate;
        this.userServiceRestTemplate = userServiceRestTemplate;
         this.ticketMapper = ticketMapper;
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
        this.destinationCancelMiles = destinationCancelMiles;
    }

    public void createTicket(TicketCreateDto ticketCreateDto){

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

        ResponseEntity<DiscountDto> discountDtoResponseEntity = null;
        try{
            //Get discount from user service
            discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/"+ticketCreateDto.getUserId()+"/discount",
                    HttpMethod.GET, null, DiscountDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Check capacity
        Integer capacity = null;
        try{

            discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/"+ticketCreateDto.getUserId()+"/discount",
                    HttpMethod.GET, null, DiscountDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }


        //Calculate price
        BigDecimal price = flightDtoResponseEntity.getBody().getPrice().multiply(discountDtoResponseEntity.getBody().getDiscount());

        //Update miles

        //Save ticket
        Ticket ticket = new Ticket(ticketCreateDto.getUserId(), ticketCreateDto.getFlightId(), price);
        ticketRepository.save(ticket);
    }

    @Override
    public Integer flightCapacity(Long flightId) {
        List<Ticket> tickets = ticketRepository.findAllByFlightId(flightId);
        return tickets.size();
    }

    @Override
    public Page<TicketDto> findAllByUserId(Long userId) {
        //TODO Sort by created date
        return ticketRepository.findAllByUserId(userId).map(ticketMapper::ticketToTicketDto);
    }

    @Override
    public void cancelTickets(CancelTicketDto cancelTicketDto){
        List<Ticket> tickets = ticketRepository.findAllByFlightId(cancelTicketDto.getFlightId());
        for (Ticket ticket:
             tickets) {
            //TODO Update status as CANCELED
            try {
                jmsTemplate.convertAndSend(destinationCancelMiles,
                        objectMapper.writeValueAsString(new CancelMilesDto(ticket.getUserId(), cancelTicketDto.getMiles())));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
