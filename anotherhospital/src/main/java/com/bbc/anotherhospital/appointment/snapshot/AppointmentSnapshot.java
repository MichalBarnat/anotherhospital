package com.bbc.anotherhospital.appointment.snapshot;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode
public class AppointmentSnapshot {
    Long id;
    Long doctorId;
    Long patientId;
    LocalDateTime dateTime;
    Double price;
}
