package com.bbc.anotherhospital.appointment.repository;

import com.bbc.anotherhospital.appointment.Appointment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class AppointmentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AppointmentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Appointment> findById(Long id) {
        String sql = "SELECT * FROM appointments WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            Appointment appointment = jdbcTemplate.queryForObject(sql, params, new AppointmentRowMapper());
            return Optional.ofNullable(appointment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static final class AppointmentRowMapper implements RowMapper<Appointment> {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getLong("id"));
             //appointment.setDoctor(rs.getLong("doctor_id"));
             //appointment.setPatient(rs.getLong("patient_id"));
             appointment.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
             appointment.setPrice(rs.getDouble("price"));
            return appointment;
        }
    }
}
