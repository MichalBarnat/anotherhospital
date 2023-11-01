package com.bbc.anotherhospital.appointment.commands;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentCommand {
    private Integer doctorId;
    private Integer patientId;
    private LocalDateTime dateTime;
    private Double price;
}
