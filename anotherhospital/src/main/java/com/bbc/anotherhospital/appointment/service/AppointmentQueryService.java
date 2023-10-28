package com.bbc.anotherhospital.appointment.service;

import com.bbc.anotherhospital.appointment.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentQueryService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AppointmentQueryService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Appointment findById(Integer id) {
        String sql = "SELECT * FROM appointment WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Appointment.class));
    }

    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointment";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Appointment.class));
    }
}
