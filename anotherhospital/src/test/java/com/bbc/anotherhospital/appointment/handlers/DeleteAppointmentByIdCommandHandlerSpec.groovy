package com.bbc.anotherhospital.appointment.handlers

import com.bbc.anotherhospital.appointment.repository.AppointmentRepository
import spock.lang.Specification

class DeleteAppointmentByIdCommandHandlerSpec extends Specification {

    AppointmentRepository appointmentRepository = Mock(AppointmentRepository)
    DeleteAppointmentByIdCommandHandlerImpl deleteHandler = new DeleteAppointmentByIdCommandHandlerImpl(appointmentRepository)

    def "should call deleteById on repository with correct id"() {
        given:
        long appointmentId = 1L

        when:
        deleteHandler.handle(appointmentId)

        then:
        1 * appointmentRepository.deleteById(appointmentId)
    }

    def "should handle deleteById correctly when called with different id"() {
        given:
        long anotherAppointmentId = 420L

        when:
        deleteHandler.handle(anotherAppointmentId)

        then:
        1 * appointmentRepository.deleteById(anotherAppointmentId)
    }
}
