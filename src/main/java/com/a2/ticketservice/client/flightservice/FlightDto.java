package com.a2.ticketservice.client.flightservice;

import java.math.BigDecimal;

public class FlightDto {
    private Long flightId;
    private String startDest;
    private String endDest;
    private Long miles;
    private BigDecimal price;

    public FlightDto() {
    }

    public FlightDto(Long flightId, String startDest, String endDest, Long miles, BigDecimal price) {
        this.flightId = flightId;
        this.startDest = startDest;
        this.endDest = endDest;
        this.miles = miles;
        this.price = price;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getStartDest() {
        return startDest;
    }

    public void setStartDest(String startDest) {
        this.startDest = startDest;
    }

    public String getEndDest() {
        return endDest;
    }

    public void setEndDest(String endDest) {
        this.endDest = endDest;
    }

    public Long getMiles() {
        return miles;
    }

    public void setMiles(Long miles) {
        this.miles = miles;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
