package com.bbc.anotherhospital.appointment.commands;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentCommand {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime dateTime;
    private Double price;
}
