package com.bbc.anotherhospital.appointment.service;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.service.DoctorQueryService;
import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.service.PatientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentQueryService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final DoctorQueryService doctorQueryService;

    private final PatientQueryService patientQueryService;

    public Appointment findById(Integer id) {
        String sql = "SELECT * FROM appointment WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        Appointment appointment = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Appointment appt = new Appointment();
            appt.setId(rs.getInt("id"));
            appt.setDateTime(rs.getObject("date_Time", LocalDateTime.class));
            appt.setPrice(rs.getDouble("price"));
            return appt;
        });

        if (appointment != null) {
            Integer doctorId = jdbcTemplate.queryForObject("SELECT doctor_id FROM appointment WHERE id = :id", params, Integer.class);
            Integer patientId = jdbcTemplate.queryForObject("SELECT patient_id FROM appointment WHERE id = :id", params, Integer.class);

            Doctor doctor = doctorQueryService.findById(doctorId);
            Patient patient = patientQueryService.findById(patientId);

            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
        }

        return appointment;
    }

    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointment";

        List<Appointment> appointments = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Appointment appt = new Appointment();
            appt.setId(rs.getInt("id"));
            appt.setDateTime(rs.getObject("date_Time", LocalDateTime.class));
            appt.setPrice(rs.getDouble("price"));
            return appt;
        });

        for (Appointment appointment : appointments) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", appointment.getId());

            Integer doctorId = jdbcTemplate.queryForObject("SELECT doctor_id FROM appointment WHERE id = :id", params, Integer.class);
            Integer patientId = jdbcTemplate.queryForObject("SELECT patient_id FROM appointment WHERE id = :id", params, Integer.class);

            Doctor doctor = doctorQueryService.findById(doctorId);
            Patient patient = patientQueryService.findById(patientId);

            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
        }

        return appointments;
    }

}
