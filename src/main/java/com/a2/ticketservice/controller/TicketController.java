package com.a2.ticketservice.controller;

import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    ResponseEntity<Void> createTicket(@RequestBody TicketCreateDto ticketCreateDto){
        ticketService.createTicket(ticketCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
