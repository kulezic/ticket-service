package com.a2.ticketservice.client.flightservice;

public class PlaneDto {

    private Long planeId;
    private String name;
    private Integer capacity;

    public PlaneDto() {
    }

    public PlaneDto(Long planeId, String name, Integer capacity) {
        this.planeId = planeId;
        this.name = name;
        this.capacity = capacity;
    }

    public Long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Long planeId) {
        this.planeId = planeId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
