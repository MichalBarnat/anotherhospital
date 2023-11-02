package com.bbc.anotherhospital.patient.repository

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import spock.lang.Specification
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.dao.EmptyResultDataAccessException
import com.bbc.anotherhospital.patient.Patient
import com.bbc.anotherhospital.exceptions.PatientNotFoundException

//@Import(PatientRepository)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestExecutionListeners([DependencyInjectionTestExecutionListener.class])
//class PatientRepositoryIT extends Specification {
//
//    @Autowired
//    private PatientRepository patientRepository
//
//    def "should find patient by id"() {
//        given:
//        Long patientId = 1L
//        Patient expectedPatient = new Patient(
//                id: patientId,
//                name: "Jan",
//                surname: "Kowalski",
//                email: "jan.kowalski@example.com",
//                pesel: "12345678901",
//                validInsurance: true
//        )
//        String sql = "SELECT * FROM patient WHERE id = :id"
//        Map<String, Object> params = [id: patientId]
//
//        jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Patient.class)) >> expectedPatient
//
//        when:
//        Patient result = patientRepository.findById(patientId)
//
//        then:
//        1 * jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Patient.class))
//        result == expectedPatient
//    }
//
//
//    def "should throw PatientNotFoundException when patient not found"() {
//        given:
//        Long patientId = 999L
//        String sql = "SELECT * FROM patient WHERE id = :id"
//        Map<String, Object> params = [id: patientId]
//
//        jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper) >> { throw new EmptyResultDataAccessException(1) }
//
//        when:
//        patientRepository.findById(patientId)
//
//        then:
//        thrown(PatientNotFoundException)
//        1 * jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper)
//    }
//}
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener,
        DbUnitTestExecutionListener
])
class PatientRepositoryIntegrationTest extends Specification {

    @Autowired
    PatientRepository patientRepository

    @DatabaseSetup("/patient-data.xml")
    def "should find patient by id"() {
        given:
        Long patientId = 1L

        when:
        Patient result = patientRepository.findById(patientId)

        then:
        result != null
        result.id == patientId
    }

    @DatabaseSetup("/patient-data.xml")
    def "should throw PatientNotFoundException when patient not found"() {
        given:
        Long nonExistingPatientId = 999L

        when:
        patientRepository.findById(nonExistingPatientId)

        then:
        thrown(PatientNotFoundException)
    }
}
