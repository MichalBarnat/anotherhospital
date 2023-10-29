package com.bbc.anotherhospital.patient.service;

import com.bbc.anotherhospital.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientQueryService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Patient findById(Integer id) {
        String sql = "SELECT * FROM patient WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Patient.class));
    }

}
