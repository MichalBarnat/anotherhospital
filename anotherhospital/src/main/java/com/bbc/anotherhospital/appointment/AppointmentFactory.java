package com.bbc.anotherhospital.appointment;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.patient.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AppointmentFactory {

    public static Appointment createAppointment() {
        return new Appointment();
    }

    public static Appointment createAppointment(long id, Doctor doctor, Patient patient, LocalDateTime dateTime, double price) {
        return Appointment.builder()
                .id(id)
                .doctor(doctor)
                .patient(patient)
                .dateTime(dateTime)
                .price(price)
                .build();
    }

    public static Appointment createAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime, double price) {
        return Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .dateTime(dateTime)
                .price(price)
                .build();
    }

}
