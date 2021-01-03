package com.a2.ticketservice.controller;

import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("flightId/{id}")
    public ResponseEntity<Integer> checkCapacity(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ticketService.flightCapacity(id), HttpStatus.OK);
    }
}
