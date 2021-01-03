package com.a2.ticketservice.listener;

import com.a2.ticketservice.dto.CancelTicketDto;
import com.a2.ticketservice.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class CancelTicketListener {

    private ObjectMapper objectMapper;
    private TicketService ticketService;

    public CancelTicketListener(ObjectMapper objectMapper, TicketService ticketService) {
        this.objectMapper = objectMapper;
        this.ticketService = ticketService;
    }

    @JmsListener(destination = "${destination.cancel-ticket}", concurrency = "5-10")
    public void handleCancelMiles(Message message){
        try {
            String jsonText = ((TextMessage)message).getText();
            CancelTicketDto cancelTicketDto = objectMapper.readValue(jsonText, CancelTicketDto.class);
            ticketService.cancelTickets(cancelTicketDto);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
