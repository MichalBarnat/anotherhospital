package com.bbc.anotherhospital.doctor.repository

import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.exceptions.DoctorNotFoundException
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import spock.lang.Specification

class DoctorRepositoryIT {
}






@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener,
        DbUnitTestExecutionListener
])
class DoctorRepositoryIntegrationTest extends Specification {

    @Autowired
    DoctorRepository doctorRepository

    @DatabaseSetup("/doctor-data.xml")
    def "should find doctor by id"() {
        given:
        Long doctorId = 1L

        when:
        Doctor result = doctorRepository.findById(doctorId)

        then:
        result != null
        result.id == doctorId
    }

    @DatabaseSetup("/doctor-data.xml")
    def "should throw DoctorNotFoundException when doctor not found"() {
        given:
        Long nonExistingDoctorId = 999L

        when:
        doctorRepository.findById(nonExistingDoctorId)

        then:
        thrown(DoctorNotFoundException)
    }
}
