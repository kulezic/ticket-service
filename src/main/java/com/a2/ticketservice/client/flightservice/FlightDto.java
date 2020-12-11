package com.a2.ticketservice.client.flightservice;

import java.math.BigDecimal;

public class FlightDto {
    private String startDest;
    private String endDest;
    private BigDecimal miles;
    private BigDecimal price;

    public FlightDto() {
    }

    public FlightDto(String startDest, String endDest, BigDecimal miles, BigDecimal price) {
        this.startDest = startDest;
        this.endDest = endDest;
        this.miles = miles;
        this.price = price;
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

    public BigDecimal getMiles() {
        return miles;
    }

    public void setMiles(BigDecimal miles) {
        this.miles = miles;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
