package com.bbc.anotherhospital.exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException() {
    }

    public DoctorNotFoundException(String message) {
        super(message);
    }
}
