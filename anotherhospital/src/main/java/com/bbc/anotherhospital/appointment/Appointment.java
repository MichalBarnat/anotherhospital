package com.bbc.anotherhospital.appointment;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    private Integer id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private Double price;
}
