package com.a2.ticketservice.dto;

public class CancelTicketDto {
    private Long flightId;
    private Long miles;

    public CancelTicketDto(Long flightId, Long miles) {
        this.flightId = flightId;
        this.miles = miles;
    }

    public CancelTicketDto() {
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getMiles() {
        return miles;
    }

    public void setMiles(Long miles) {
        this.miles = miles;
    }
}
