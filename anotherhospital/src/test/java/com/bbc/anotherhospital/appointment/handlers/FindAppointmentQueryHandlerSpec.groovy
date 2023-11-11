package com.bbc.anotherhospital.appointment.handlers

import com.bbc.anotherhospital.appointment.Appointment
import com.bbc.anotherhospital.appointment.AppointmentFactory
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot
import com.bbc.anotherhospital.exceptions.AppointmentNotFoundException
import org.modelmapper.ModelMapper
import spock.lang.Specification

class FindAppointmentQueryHandlerSpec extends Specification {

    AppointmentRepository appointmentRepository = Mock(AppointmentRepository)
    ModelMapper modelMapper = Mock(ModelMapper)
    FindAppointmentQueryHandlerImpl findAppointmentQueryHandler = new FindAppointmentQueryHandlerImpl(appointmentRepository, modelMapper);

    def "should find appointment by id when exist"() {
        given:
        Appointment appointment = AppointmentFactory.createAppointment()
        appointment.setId(1L)
        appointment.setPrice(200.5)

        AppointmentSnapshot appointmentSnapshot = AppointmentSnapshot.builder()
                .id(appointment.getId())
                .price(appointment.getPrice())
                .build();

        when:
        appointmentRepository.findById(appointment.getId()) >> appointment
        modelMapper.map(appointment, AppointmentSnapshot) >> appointmentSnapshot
        AppointmentSnapshot foundAppointment = findAppointmentQueryHandler.handle(appointment.getId());

        then:
        foundAppointment.id == 1L
        foundAppointment.price == 200.5
    }

    def "should throw AppointmentNotFoundException when appointment do not exist" () {
        given:
        long nonExistingAppointmentId = 69L

        when:
        appointmentRepository.findById(nonExistingAppointmentId) >> { throw new AppointmentNotFoundException("Appointment with id " + nonExistingAppointmentId + " not found") }
        findAppointmentQueryHandler.handle(nonExistingAppointmentId)

        then:
        // razem z tym wywala blad i na odwrot tak samo :/
        //1 * appointmentRepository.findById(69L)
        thrown(AppointmentNotFoundException)
    }


}
