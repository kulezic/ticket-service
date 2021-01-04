package com.a2.ticketservice.client.flightservice;

public class FlightCapacityDto {
    private Integer capacity;

    public FlightCapacityDto() {
    }

    public FlightCapacityDto(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
