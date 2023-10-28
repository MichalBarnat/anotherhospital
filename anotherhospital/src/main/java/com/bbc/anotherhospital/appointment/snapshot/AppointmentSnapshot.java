package com.bbc.anotherhospital.appointment.snapshot;

import lombok.Builder;
import lombok.Value;

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
