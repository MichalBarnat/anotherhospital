package com.bbc.anotherhospital.patient.repository;

import com.bbc.anotherhospital.exceptions.PatientNotFoundException;
import com.bbc.anotherhospital.patient.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PatientRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PatientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Patient findById(Long id) {
        String sql = "SELECT * FROM patient WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Patient.class));
        } catch (EmptyResultDataAccessException e) {
            throw new PatientNotFoundException("Patient with id " + id + " not found");
        }
    }
}
