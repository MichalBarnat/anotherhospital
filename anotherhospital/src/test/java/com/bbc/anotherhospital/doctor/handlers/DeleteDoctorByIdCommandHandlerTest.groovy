package com.bbc.anotherhospital.doctor.handlers

import com.bbc.anotherhospital.doctor.repository.DoctorRepository
import spock.lang.Specification

class DeleteDoctorByIdCommandHandlerTest extends Specification {
    DoctorRepository doctorRepository = Mock(DoctorRepository)
    DeleteDoctorByIdCommandHandlerImpl deleteHandler = new DeleteDoctorByIdCommandHandlerImpl(doctorRepository)

    def "should call deleteById on repository with correct id"() {
        given:
        long doctorId = 1L

        when:
        deleteHandler.handle(doctorId)

        then:
        1 * doctorRepository.deleteById(doctorId)
    }

    def "should handle deleteById correctly when called with different id"() {
        given:
        long doctorId = 100L

        when:
        deleteHandler.handle(doctorId)

        then:
        1 * doctorRepository.deleteById(doctorId)
    }


}
