package com.bbc.anotherhospital.appointment.repository;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.AppointmentFactory;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentPageCommand;
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand;
import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.handlers.FindDoctorQueryHandler;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import com.bbc.anotherhospital.exceptions.AppointmentIsNotAvailableException;
import com.bbc.anotherhospital.exceptions.AppointmentNotFoundException;
import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.handlers.FindPatientQueryHandler;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Appointment save(CreateAppointmentCommand command) {
        if (appointmentIsAvailable(command)) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "INSERT INTO appointment (doctor_id, patient_id, date_time, price) VALUES (:doctorId, :patientId, :dateTime, :price)";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("doctorId", command.getDoctorId());
            params.addValue("patientId", command.getPatientId());
            params.addValue("dateTime", command.getDateTime());
            params.addValue("price", command.getPrice());

            jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

            Long newAppointmentId = keyHolder.getKey().longValue();
            return findById(newAppointmentId);
        } else {
            throw new AppointmentIsNotAvailableException("Appointment is not available at: " + command.getDateTime());
        }
    }

    public Appointment findById(Long id) {
        String sql = "SELECT * FROM appointment WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        Appointment appointment;
        try {
            appointment = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                Appointment appt = AppointmentFactory.createAppointment();
                appt.setId(rs.getLong("id"));
                appt.setDateTime(rs.getObject("date_time", LocalDateTime.class));
                appt.setPrice(rs.getDouble("price"));
                return appt;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new AppointmentNotFoundException("Appointment with id " + id + " not found");
        }

        if (appointment != null) {
            Long doctorId = jdbcTemplate.queryForObject("SELECT doctor_id FROM appointment WHERE id = :id", params, Long.class);
            Long patientId = jdbcTemplate.queryForObject("SELECT patient_id FROM appointment WHERE id = :id", params, Long.class);

            if (doctorId != null) {
                DoctorSnapshot doctorSnapshot = findDoctorQueryHandler.handle(doctorId);
                appointment.setDoctor(modelMapper.map(doctorSnapshot, Doctor.class));
            } else {
                appointment.setDoctor(null);
            }

            if (patientId != null) {
                PatientSnapshot patientSnapshot = findPatientQueryHandler.handle(patientId);
                appointment.setPatient(modelMapper.map(patientSnapshot, Patient.class));
            } else {
                appointment.setPatient(null);
            }
        }

        return appointment;
    }

    public List<Appointment> findAll(CreateAppointmentPageCommand command) {
        int offset = command.getPageNumber() * command.getPageSize();
        String sql = "SELECT * FROM appointment ORDER BY " + command.getSortBy() + " " + command.getSortDirection() +
                " LIMIT :limit OFFSET :offset";

        Map<String, Object> params = new HashMap<>();
        params.put("limit", command.getPageSize());
        params.put("offset", offset);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Appointment appointment = AppointmentFactory.createAppointment();
            appointment.setId(rs.getLong("id"));
            appointment.setDateTime(rs.getObject("date_time", LocalDateTime.class));
            appointment.setPrice(rs.getDouble("price"));

            Long doctorId = rs.getLong("doctor_id");
            Long patientId = rs.getLong("patient_id");

            if (doctorId != null) {
                DoctorSnapshot doctorSnapshot = findDoctorQueryHandler.handle(doctorId);
                appointment.setDoctor(modelMapper.map(doctorSnapshot, Doctor.class));
            }

            if (patientId != null) {
                PatientSnapshot patientSnapshot = findPatientQueryHandler.handle(patientId);
                appointment.setPatient(modelMapper.map(patientSnapshot, Patient.class));
            }

            return appointment;
        });
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM appointment WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcTemplate.update(sql, params);
    }

    public Appointment edit(Long id, UpdateAppointmentCommand command) {
        Appointment currentAppointment = findById(id);

        String sql = "UPDATE appointment SET doctor_id = :doctorId, patient_id = :patientId, date_time = :dateTime, price = :price WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("doctorId", command.getDoctorId());
        params.addValue("patientId", command.getPatientId());
        params.addValue("dateTime", command.getDateTime());
        params.addValue("price", command.getPrice());

        jdbcTemplate.update(sql, params);

        return findById(id);
    }

    public Appointment editPartially(Long id, UpdateAppointmentCommand command) {
        Appointment currentAppointment = findById(id);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        params.addValue("doctorId", command.getDoctorId() != null ? command.getDoctorId() : currentAppointment.getDoctor() != null ? currentAppointment.getDoctor().getId() : null);

        params.addValue("patientId", command.getPatientId() != null ? command.getPatientId() : currentAppointment.getPatient() != null ? currentAppointment.getPatient().getId() : null);

        params.addValue("dateTime", Optional.ofNullable(command.getDateTime()).orElse(currentAppointment.getDateTime()));
        params.addValue("price", Optional.ofNullable(command.getPrice()).orElse(currentAppointment.getPrice()));

        String sql = "UPDATE appointment SET doctor_id = :doctorId, patient_id = :patientId, date_time = :dateTime, price = :price WHERE id = :id";

        jdbcTemplate.update(sql, params);

        return findById(id);
    }

    public List<Appointment> findAllByDoctorId(Long doctorId) {
        String sql = "SELECT * FROM appointment WHERE doctor_id = :doctorId";
        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", doctorId);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Appointment.class));
    }

    public List<Appointment> findAllByPatientId(Long patientId) {
        String sql = "SELECT * FROM appointment WHERE patient_id = :patientId";
        Map<String, Object> params = new HashMap<>();
        params.put("patientId", patientId);
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Appointment.class));
    }

    // TODO przeniesc do CreateAppointmentCommandHandler
    public boolean appointmentIsAvailable(CreateAppointmentCommand command) {
        LocalDateTime proposedDateTime = command.getDateTime();
        LocalDateTime proposedEndDateTime = proposedDateTime.plusMinutes(15);

        List<Appointment> doctorAppointments = findAllByDoctorId(command.getDoctorId());
        List<Appointment> patientAppointments = findAllByPatientId(command.getPatientId());

        boolean doctorTimeValidation = doctorAppointments.stream()
                .anyMatch(appointment -> {
                    LocalDateTime appointmentStart = appointment.getDateTime();
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(15);
                    return (proposedDateTime.isBefore(appointmentEnd) && proposedEndDateTime.isAfter(appointmentStart));
                });

        boolean patientTimeValidation = patientAppointments.stream()
                .anyMatch(appointment -> {
                    LocalDateTime appointmentStart = appointment.getDateTime();
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(15);
                    return (proposedDateTime.isBefore(appointmentEnd) && proposedEndDateTime.isAfter(appointmentStart));
                });

        return !doctorTimeValidation && !patientTimeValidation;
    }

}
