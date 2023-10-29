package com.bbc.anotherhospital.appointment.snapshot;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
public class AppointmentSnapshot {
    Integer id;
    Integer doctorId;
    Integer patientId;
    LocalDateTime dateTime;
    Double price;
}
