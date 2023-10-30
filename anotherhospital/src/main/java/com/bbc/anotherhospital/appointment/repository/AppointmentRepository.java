package com.bbc.anotherhospital.appointment.repository;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.handlers.FindDoctorQueryHandler;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import com.bbc.anotherhospital.exceptions.AppointmentNotFoundException;
import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.handlers.FindPatientQueryHandler;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AppointmentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FindDoctorQueryHandler findDoctorQueryHandler;
    private final FindPatientQueryHandler findPatientQueryHandler;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentRepository(NamedParameterJdbcTemplate jdbcTemplate, FindDoctorQueryHandler findDoctorQueryHandler, FindPatientQueryHandler findPatientQueryHandler, ModelMapper modelMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.findDoctorQueryHandler = findDoctorQueryHandler;
        this.findPatientQueryHandler = findPatientQueryHandler;
        this.modelMapper = modelMapper;
    }

    public Appointment findById(Long id) {
        String sql = "SELECT * FROM appointment WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        Appointment appointment;
        try {
            appointment = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                Appointment appt = new Appointment();
                appt.setId(rs.getLong("id"));
                appt.setDateTime(rs.getObject("date_Time", LocalDateTime.class));
                appt.setPrice(rs.getDouble("price"));
                return appt;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new AppointmentNotFoundException("Appointment with id " + id + " not found");
        }

        if (appointment != null) {
            Long doctorId = jdbcTemplate.queryForObject("SELECT doctor_id FROM appointment WHERE id = :id", params, Long.class);
            Long patientId = jdbcTemplate.queryForObject("SELECT patient_id FROM appointment WHERE id = :id", params, Long.class);

            DoctorSnapshot doctorSnapshot = findDoctorQueryHandler.handle(doctorId);
            PatientSnapshot patientSnapshot = findPatientQueryHandler.handle(patientId);

            appointment.setDoctor(modelMapper.map(doctorSnapshot, Doctor.class));
            appointment.setPatient(modelMapper.map(patientSnapshot, Patient.class));
        }

        return appointment;
    }


}
