package com.bbc.anotherhospital.exceptions;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException() {
    }

    public PatientNotFoundException(String message) {
        super(message);
    }
}
