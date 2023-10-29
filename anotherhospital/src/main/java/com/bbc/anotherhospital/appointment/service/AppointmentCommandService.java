package com.bbc.anotherhospital.appointment.service;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.AppointmentFactory;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentCommandService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AppointmentFactory appointmentFactory;
    private final AppointmentQueryService appointmentQueryService;

    public Appointment createAppointment(CreateAppointmentCommand command) {

        Appointment newAppointment = appointmentFactory.createAppointment(
                command.getDoctorId(),
                command.getPatientId(),
                command.getDateTime(),
                command.getPrice()
        );

        String sql = "INSERT INTO appointment (doctor_id, patient_id, date_time, price) VALUES (:doctorId, :patientId, :dateTime, :price)";
        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", newAppointment.getDoctor().getId());
        params.put("patientId", newAppointment.getPatient().getId());
        params.put("dateTime", newAppointment.getDateTime());
        params.put("price", newAppointment.getPrice());

        jdbcTemplate.update(sql, params);


        return newAppointment;
    }


}
