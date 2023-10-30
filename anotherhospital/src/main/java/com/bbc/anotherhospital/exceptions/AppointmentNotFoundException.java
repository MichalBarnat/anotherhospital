package com.bbc.anotherhospital.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException() {
    }

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
