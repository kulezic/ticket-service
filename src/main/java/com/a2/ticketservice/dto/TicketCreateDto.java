package com.a2.ticketservice.dto;

public class TicketCreateDto {

    private Long userId;
    private Long flightId;

    public TicketCreateDto() {
    }

    public TicketCreateDto(Long userId, Long flightId) {
        this.userId = userId;
        this.flightId = flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
