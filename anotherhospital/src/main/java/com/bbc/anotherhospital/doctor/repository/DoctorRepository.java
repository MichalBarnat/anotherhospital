package com.bbc.anotherhospital.doctor.repository;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.exceptions.DoctorNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DoctorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DoctorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Doctor findById(Long id) {
        String sql = "SELECT * FROM doctor WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Doctor.class));
        } catch (EmptyResultDataAccessException e) {
            throw new DoctorNotFoundException("Doctor with id " + id + " not found");
        }
    }
}
