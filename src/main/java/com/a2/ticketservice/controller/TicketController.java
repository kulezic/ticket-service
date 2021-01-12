package com.a2.ticketservice.controller;

import com.a2.ticketservice.dto.SoldTicketsDto;
import com.a2.ticketservice.dto.TicketCreateDto;
import com.a2.ticketservice.dto.TicketDto;
import com.a2.ticketservice.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    //TODO Add card to post mapping
    @PostMapping
    ResponseEntity<Void> createTicket(@RequestBody TicketCreateDto ticketCreateDto){
        System.out.println(ticketCreateDto.getUserId() + " user - flight" + ticketCreateDto.getFlightId());
        ticketService.createTicket(ticketCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("flight/{id}")
    public ResponseEntity<SoldTicketsDto> checkCapacity(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ticketService.flightCapacity(id), HttpStatus.OK);
    }

    //TODO Sort by created date
    @GetMapping("/user/{id}")
    public ResponseEntity<List<TicketDto>> findAllByUserId(@PathVariable("id") Long id){
        return new ResponseEntity<>(ticketService.findAllByUserId(id), HttpStatus.OK);
    }
}
