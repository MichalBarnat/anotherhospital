package com.bbc.anotherhospital.exceptions;

public class AppointmentIsNotAvailableException extends RuntimeException{
    public AppointmentIsNotAvailableException() {
    }

    public AppointmentIsNotAvailableException(String message) {
        super(message);
    }
}
