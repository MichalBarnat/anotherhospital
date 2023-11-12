package com.bbc.anotherhospital.patient.repository;

import com.bbc.anotherhospital.exceptions.PatientNotFoundException;
import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.PatientFactory;
import com.bbc.anotherhospital.patient.commands.CreatePatientCommand;
import com.bbc.anotherhospital.patient.commands.UpdatePatientCommand;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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
        // TODO wyniesc do handlera
        try {
            return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Patient.class));
        } catch (EmptyResultDataAccessException e) {
            throw new PatientNotFoundException("Patient with id " + id + " not found");
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM patient WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcTemplate.update(sql, params);
    }

    public List<Patient> findAll(CreatePatientCommand command) {
        String sql = "SELECT * FROM patient";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Patient patient = PatientFactory.createPatient();
            patient.setId(rs.getLong("id"));
            patient.setName(rs.getString("name"));
            patient.setSurname(rs.getString("surname"));
            patient.setEmail(rs.getString("email"));
            patient.setPesel(rs.getString("pesel"));
            patient.setValidInsurance(rs.getBoolean("validInsurance"));

            return patient;
        });
    }

    public Patient edit(Long id, UpdatePatientCommand command) {
        Patient currentPatient = findById(id);

        String sql = "UPDATE patient SET name = :name, surname = :surname, email = :email, pesel = :pesel," +
                " valid_insurance = :validInsurance WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("name", command.getName());
        params.addValue("surname", command.getSurname());
        params.addValue("email", command.getEmail());
        params.addValue("pesel", command.getPesel());
        params.addValue("validInsurance", command.getValidInsurance());

        jdbcTemplate.update(sql, params);

        return findById(id);
    }

    public Patient save(CreatePatientCommand command) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO patient (name, surname, email, pesel, valid_insurance) VALUES (:name, :surname," +
                " :email, :pesel, :validInsurance)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", command.getName());
        params.addValue("surname", command.getSurname());
        params.addValue("email", command.getEmail());
        params.addValue("pesel", command.getPesel());
        params.addValue("validInsurance", command.getValidInsurance());

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        keyHolder.getKey().longValue();

        return findById(keyHolder.getKey().longValue());
    }

    public Patient editPatrially(Long id, UpdatePatientCommand command) {
        Patient currentPatient = findById(id);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("name", command.getName());
        params.addValue("surname", command.getSurname());
        params.addValue("email", command.getEmail());
        params.addValue("pesel", command.getPesel());
        params.addValue("validInsurance", command.getValidInsurance());

        String sql = "UPDATE patient SET name = :name, surname = :surname, email = :email," +
                " pesel = :pesel, valid_insurance = :validInsurance";

        jdbcTemplate.update(sql, params);

        return findById(id);
    }
}
