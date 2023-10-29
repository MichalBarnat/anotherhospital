package com.bbc.anotherhospital.appointment;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.service.DoctorQueryService;
import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.service.PatientQueryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AppointmentFactory {

    private final DoctorQueryService doctorQueryService;
    private final PatientQueryService patientQueryService;

    public AppointmentFactory(DoctorQueryService doctorQueryService, PatientQueryService patientQueryService) {
        this.doctorQueryService = doctorQueryService;
        this.patientQueryService = patientQueryService;
    }

    public Appointment createAppointment(Integer doctorId, Integer patientId, LocalDateTime dateTime, Double price) {
        Doctor doctor = doctorQueryService.findById(doctorId);
        if (doctor == null) {
            throw new IllegalStateException("Doctor with ID " + doctorId + " not found");
        }

        Patient patient = patientQueryService.findById(patientId);
        if (patient == null) {
            throw new IllegalStateException("Patient with ID " + patientId + " not found");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dateTime);
        appointment.setPrice(price);
        return appointment;
    }

}
