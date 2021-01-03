package com.a2.ticketservice.dto;

public class IncrementMilesDto {
    private Long userId;
    private Long miles;

    public IncrementMilesDto() {
    }

    public IncrementMilesDto(Long userId,
                             Long miles) {
        this.userId = userId;
        this.miles = miles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMiles() {
        return miles;
    }

    public void setMiles(Long miles) {
        this.miles = miles;
    }
}
