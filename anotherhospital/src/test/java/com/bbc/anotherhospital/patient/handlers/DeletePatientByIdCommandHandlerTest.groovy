package com.bbc.anotherhospital.patient.handlers

import com.bbc.anotherhospital.patient.repository.PatientRepository
import spock.lang.Specification

class DeletePatientByIdCommandHandlerTest extends Specification {
    PatientRepository patientRepository = Mock(PatientRepository)
    DeletePatientByIdCommandHandlerImpl deleteHandler = new DeletePatientByIdCommandHandlerImpl(patientRepository)

    def "should call deleteById on repository with correct id" () {

        given:
        long patientId = 1L

        when:
        deleteHandler.handle(patientId)

        then:
        1* patientRepository.deleteById(patientId)
    }

    def "should handle deleteById correctly when called with diffrent id"() {

        given:
        long patientId = 222L

        when:
        deleteHandler.handle(patientId)

        then:
        1* patientRepository.deleteById(patientId)

    }

}
