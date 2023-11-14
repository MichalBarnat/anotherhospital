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

    def "should find appointment by id when exist state verification"() {
        given:
        Appointment appointment = AppointmentFactory.createAppointment()
        appointment.setId(1L)
        appointment.setPrice(200.5)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointment.getId())
                .price(appointment.getPrice())
                .build();

        when:
        appointmentRepository.findById(appointment.getId()) >> appointment
        modelMapper.map(appointment, AppointmentSnapshot) >> expectedSnapshot
        AppointmentSnapshot foundAppointment = findAppointmentQueryHandler.handle(appointment.getId())

        then:
        foundAppointment == expectedSnapshot
    }

    def "should find appointment by id when exist behavior verification"() {
        given:
        Appointment appointment = AppointmentFactory.createAppointment()
        appointment.setId(1L)
        appointment.setPrice(200.5)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointment.getId())
                .price(appointment.getPrice())
                .build();

        when:
        appointmentRepository.findById(appointment.getId()) >> appointment
        modelMapper.map(appointment, AppointmentSnapshot) >> expectedSnapshot
        AppointmentSnapshot foundAppointment = findAppointmentQueryHandler.handle(appointment.getId())

        then:
        1 * appointmentRepository.findById(1L)
        //dlaczego z wildcardem dziala a z appointment juz nie ???
        1 * modelMapper.map(_, AppointmentSnapshot)
    }

//    def "should throw AppointmentNotFoundException when appointment do not exist state verification" () {
//        given:
//        long nonExistingAppointmentId = 69L
//        appointmentRepository.findById(nonExistingAppointmentId) >> { throw new AppointmentNotFoundException("Appointment with id " + nonExistingAppointmentId + " not found") }
//
//        when:
//        findAppointmentQueryHandler.handle(nonExistingAppointmentId)
//
//        then:
//        thrown(AppointmentNotFoundException)
//    }
//
//    def "should throw AppointmentNotFoundException when appointment do not exist behavior verification" () {
//        given:
//        long nonExistingAppointmentId = 69L
//        appointmentRepository.findById(nonExistingAppointmentId) >> { throw new AppointmentNotFoundException("Appointment with id " + nonExistingAppointmentId + " not found") }
//
//        when:
//        findAppointmentQueryHandler.handle(nonExistingAppointmentId)
//
//        then:
//        1 * appointmentRepository.findById(nonExistingAppointmentId)
//    }
//
//    def "should throw AppointmentNotFoundException when appointment do not exist ERROR" () {
//        given:
//        long nonExistingAppointmentId = 69L
//        appointmentRepository.findById(nonExistingAppointmentId) >> { throw new AppointmentNotFoundException("Appointment with id " + nonExistingAppointmentId + " not found") }
//
//        when:
//        findAppointmentQueryHandler.handle(nonExistingAppointmentId)
//
//        then:
//        1 * appointmentRepository.findById(69L)
//        thrown(AppointmentNotFoundException)
//    }

    def "should throw AppointmentNotFoundException when appointment do not exist PROPER WAY" () {
        given:
        long nonExistingAppointmentId = 69L

        when:
        findAppointmentQueryHandler.handle(nonExistingAppointmentId)

        then:
        def ex = thrown(AppointmentNotFoundException)
        ex.message == "Appointment with id $nonExistingAppointmentId not found"


        1 * appointmentRepository.findById(69L) >> {
            throw new AppointmentNotFoundException("Appointment with id " + nonExistingAppointmentId + " not found")
        }
    }

}
