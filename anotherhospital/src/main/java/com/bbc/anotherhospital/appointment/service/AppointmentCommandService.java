package com.bbc.anotherhospital.appointment.service;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.AppointmentFactory;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointmentCommandService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AppointmentFactory appointmentFactory;
    private final AppointmentQueryService appointmentQueryService;


    public Integer createAppointment(CreateAppointmentCommand command) {
        Appointment newAppointment = appointmentFactory.createAppointment(
                command.getDoctorId(),
                command.getPatientId(),
                command.getDateTime(),
                command.getPrice()
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO appointment (doctor_id, patient_id, date_time, price) VALUES (:doctorId, :patientId, :dateTime, :price)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("doctorId", newAppointment.getDoctor().getId())
                .addValue("patientId", newAppointment.getPatient().getId())
                .addValue("dateTime", newAppointment.getDateTime())
                .addValue("price", newAppointment.getPrice());

        jdbcTemplate.update(sql, parameters, keyHolder, new String[] {"id"});

        return keyHolder.getKey().intValue();
    }


}
