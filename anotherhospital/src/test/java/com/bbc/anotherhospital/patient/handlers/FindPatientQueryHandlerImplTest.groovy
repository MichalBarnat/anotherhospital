package com.bbc.anotherhospital.patient.handlers

import com.bbc.anotherhospital.exceptions.PatientNotFoundException
import com.bbc.anotherhospital.patient.Patient
import com.bbc.anotherhospital.patient.repository.PatientRepository
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot

import spock.lang.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.modelmapper.ModelMapper

class FindPatientQueryHandlerImplTest extends Specification {

    @Autowired
    PatientRepository patientRepository = Mock()

    @Autowired
    ModelMapper modelMapper = Mock()

    FindPatientQueryHandlerImpl service

    def setup() {
        service = new FindPatientQueryHandlerImpl(patientRepository, modelMapper)
    }

    def "should return PatientSnapshot for given patient id"() {
        given:
        Long patientId = 1L
        Patient patient = Patient.builder()
                .id(patientId)
                .name("Jan")
                .surname("Kowalski")
                .email("jan.kowalski@example.com")
                .pesel("12345678901")
                .validInsurance(true)
                .build()
        PatientSnapshot expectedSnapshot = PatientSnapshot.builder()
                .id(patientId)
                .name("Jan")
                .surname("Kowalski")
                .email("jan.kowalski@example.com")
                .pesel("12345678901")
                .validInsurance(true)
                .build()

        when:
        patientRepository.findById(patientId) >> patient
        modelMapper.map(patient, PatientSnapshot.class) >> expectedSnapshot

        PatientSnapshot result = service.handle(patientId)

        then:
        1 * patientRepository.findById(patientId)
        1 * modelMapper.map(patient, PatientSnapshot.class)
        result == expectedSnapshot
    }

    def "should throw PatientNotFoundException when patient not found"() {
        given:
        Long patientId = 2L

        when:
        patientRepository.findById(patientId) >> { throw new PatientNotFoundException("Patient with id $patientId not found") }

        then:
        service.handle(patientId) >> { e -> e instanceof PatientNotFoundException }
        1 * patientRepository.findById(patientId)
        0 * modelMapper.map(_, _)
    }
}
