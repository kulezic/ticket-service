package com.a2.ticketservice.listener;

import com.a2.ticketservice.client.flightservice.FlightCancelDto;
import com.a2.ticketservice.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class FlightCancelListener {

    private ObjectMapper objectMapper;
    private TicketService ticketService;

    public FlightCancelListener(ObjectMapper objectMapper, TicketService ticketService) {
        this.objectMapper = objectMapper;
        this.ticketService = ticketService;
    }
    //, concurrency = "5-10"
    @JmsListener(destination = "${destination.cancel-flight}")
    public void handleCancelMiles(Message message){
        try {
            String jsonText = ((TextMessage)message).getText();
            FlightCancelDto flightCancelDto = objectMapper.readValue(jsonText, FlightCancelDto.class);
            ticketService.cancelTickets(flightCancelDto);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
