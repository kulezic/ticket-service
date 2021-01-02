package com.a2.ticketservice.dto;

import java.math.BigDecimal;

public class TicketDto {
    private Long id;
    private Long userId;
    private Long flightId;
    private BigDecimal price;
    private String ticketStatus;

    public TicketDto(Long id, Long userId, Long flightId, BigDecimal price, String ticketStatus) {
        this.id = id;
        this.userId = userId;
        this.flightId = flightId;
        this.price = price;
        this.ticketStatus = ticketStatus;
    }

    public TicketDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
