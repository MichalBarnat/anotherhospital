package com.bbc.anotherhospital.exceptions;

public class NoGeneratedKeyException extends RuntimeException{
    public NoGeneratedKeyException() {
    }

    public NoGeneratedKeyException(String message) {
        super(message);
    }
}
