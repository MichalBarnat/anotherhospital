package com.bbc.anotherhospital.doctor.repository;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.DoctorFactory;
import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.commands.UpdateDoctorCommand;
import com.bbc.anotherhospital.exceptions.DoctorNotFoundException;
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
public class DoctorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DoctorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Doctor findById(Long id) {
        String sql = "SELECT * FROM doctor WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        // TODO wyniesc do handlera
        try {
            return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Doctor.class));
        } catch (EmptyResultDataAccessException e) {
            throw new DoctorNotFoundException("Doctor with id " + id + " not found");
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM doctor WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcTemplate.update(sql, params);
    }

    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctor";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Doctor doctor = DoctorFactory.createDoctor();
            doctor.setId(rs.getLong("id"));
            doctor.setName(rs.getString("name"));
            doctor.setSurname(rs.getString("surname"));
            doctor.setSpeciality(rs.getString("speciality"));
            doctor.setEmail(rs.getString("email"));
            doctor.setRate(rs.getInt("rate"));
            doctor.setPesel(rs.getString("pesel"));
            doctor.setValidInsurance(rs.getBoolean("validInsurance"));

            return doctor;
        });
    }

    public Doctor edit(Long id, UpdateDoctorCommand command) {
        Doctor currentDoctor = findById(id);

        String sql = "UPDATE doctor SET name = :name, surname = :surname, speciality = :speciality, email = :email, rate = :rate, pesel = :pesel," +
                " valid_insurance = :validInsurance WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("name", command.getName());
        params.addValue("surname", command.getSurname());
        params.addValue("speciality", command.getSpeciality());
        params.addValue("email", command.getEmail());
        params.addValue("rate", command.getRate());
        params.addValue("pesel", command.getPesel());
        params.addValue("validInsurance", command.getValidInsurance());

        jdbcTemplate.update(sql, params);
        return findById(id);
    }

    public Doctor save(CreateDoctorCommand command) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO doctor (name, surname, speciality, email, rate, pesel, valid_insurance) VALUES (:name, :surname," +
                ":speciality, :email, :rate, :pesel, :validInsurance)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", command.getName());
        params.addValue("surname", command.getSurname());
        params.addValue("speciality", command.getSpeciality());
        params.addValue("email", command.getEmail());
        params.addValue("rate", command.getRate());
        params.addValue("pesel", command.getPesel());
        params.addValue("validInsurance", command.getValidInsurance());

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        keyHolder.getKey().longValue();

        return findById(keyHolder.getKey().longValue());
    }


}
