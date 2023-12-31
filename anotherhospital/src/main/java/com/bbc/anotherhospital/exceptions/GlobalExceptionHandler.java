package com.bbc.anotherhospital.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorMessage>  handleDoctorNotFoundException(DoctorNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .dateTime(LocalDateTime.now())
                .code(NOT_FOUND.value())
                .status(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build(), NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorMessage>  handleAppointmentNotFoundException(AppointmentNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .dateTime(LocalDateTime.now())
                .code(NOT_FOUND.value())
                .status(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build(), NOT_FOUND);
    }

    @ExceptionHandler(AppointmentIsNotAvailableException.class)
    public ResponseEntity<ErrorMessage>  handleAppointmentIsNotAvailableException(AppointmentIsNotAvailableException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .dateTime(LocalDateTime.now())
                .code(BAD_REQUEST.value())
                .status(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build(), BAD_REQUEST);
    }

}
