package com.bbc.anotherhospital.doctor.service;

import com.bbc.anotherhospital.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DoctorQueryService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DoctorQueryService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Doctor findById(Integer id) {
        String sql = "SELECT * FROM doctor WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(sql,params, new BeanPropertyRowMapper<>(Doctor.class));
    }
}
