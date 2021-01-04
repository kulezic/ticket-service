package com.a2.ticketservice.exception;

import org.springframework.http.HttpStatus;

public class CapacityFullException extends CustomException{
    public CapacityFullException(String message) {
        super(message, ErrorCode.CAPACITY_FULL, HttpStatus.I_AM_A_TEAPOT);
    }
}
