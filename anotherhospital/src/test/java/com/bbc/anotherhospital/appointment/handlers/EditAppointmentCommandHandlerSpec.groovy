package com.bbc.anotherhospital.appointment.handlers

import com.bbc.anotherhospital.appointment.Appointment
import com.bbc.anotherhospital.appointment.AppointmentFactory
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot
import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.doctor.DoctorFactory
import com.bbc.anotherhospital.exceptions.AppointmentNotFoundException
import org.modelmapper.ModelMapper
import spock.lang.Specification


class EditAppointmentCommandHandlerSpec extends Specification {

    AppointmentRepository appointmentRepository = Mock(AppointmentRepository)
    ModelMapper modelMapper = Mock(ModelMapper)
    EditAppointmentCommandHandlerImpl editAppointmentHandler = new EditAppointmentCommandHandlerImpl(appointmentRepository, modelMapper)

    def "should handle edit command and return appointment snapshot state verification"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        Doctor doctor = DoctorFactory.createDoctor()
        doctor.setId(2L)

        Appointment editedAppointment = AppointmentFactory.createAppointment()
        editedAppointment.setId(appointmentId)
        editedAppointment.setDoctor(doctor)
        editedAppointment.setPrice(100.0)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointmentId)
                .doctorId(2L)
                .price(100.0)
                .build()

        appointmentRepository.edit(appointmentId, command) >> editedAppointment
        modelMapper.map(editedAppointment, AppointmentSnapshot.class) >> expectedSnapshot

        when:
        AppointmentSnapshot actualSnapshot = editAppointmentHandler.handle(appointmentId, command)

        then:
        actualSnapshot == expectedSnapshot
    }

    def "should handle edit command and return appointment snapshot behavior verification1"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        Doctor doctor = DoctorFactory.createDoctor()
        doctor.setId(2L)

        Appointment editedAppointment = AppointmentFactory.createAppointment()
        editedAppointment.setId(appointmentId)
        editedAppointment.setDoctor(doctor)
        editedAppointment.setPrice(100.0)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointmentId)
                .doctorId(2L)
                .price(100.0)
                .build()

        appointmentRepository.edit(appointmentId, command) >> editedAppointment
        modelMapper.map(editedAppointment, AppointmentSnapshot.class) >> expectedSnapshot

        when:
        AppointmentSnapshot actualSnapshot = editAppointmentHandler.handle(appointmentId, command)

        then:

        1 * appointmentRepository.edit(appointmentId, command)
    }

    def "should handle edit command and return appointment snapshot behavior verification2"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        Doctor doctor = DoctorFactory.createDoctor()
        doctor.setId(2L)

        Appointment editedAppointment = AppointmentFactory.createAppointment()
        editedAppointment.setId(appointmentId)
        editedAppointment.setDoctor(doctor)
        editedAppointment.setPrice(100.0)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointmentId)
                .doctorId(2L)
                .price(100.0)
                .build()

        appointmentRepository.edit(appointmentId, command) >> editedAppointment
        modelMapper.map(editedAppointment, AppointmentSnapshot.class) >> expectedSnapshot

        when:
        AppointmentSnapshot actualSnapshot = editAppointmentHandler.handle(appointmentId, command)

        then:
        1 * modelMapper.map(editedAppointment, AppointmentSnapshot.class)
    }

    def "should handle edit command and return appointment snapshot behavior verification 3 ERROR"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        Doctor doctor = DoctorFactory.createDoctor()
        doctor.setId(2L)

        Appointment editedAppointment = AppointmentFactory.createAppointment()
        editedAppointment.setId(appointmentId)
        editedAppointment.setDoctor(doctor)
        editedAppointment.setPrice(100.0)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointmentId)
                .doctorId(2L)
                .price(100.0)
                .build()

        appointmentRepository.edit(appointmentId, command) >> editedAppointment
        modelMapper.map(editedAppointment, AppointmentSnapshot) >> expectedSnapshot

        when:
        AppointmentSnapshot actualSnapshot = editAppointmentHandler.handle(appointmentId, command)

        then:
        1 * appointmentRepository.edit(appointmentId, command)
        1 * modelMapper.map(_, AppointmentSnapshot)
    }

    def "should throw exception when appointment not found state verification"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        appointmentRepository.edit(appointmentId, command) >> { throw new AppointmentNotFoundException("Appointment not found") }
        appointmentRepository.findById(appointmentId) >> { throw new AppointmentNotFoundException("Appointment not found") }

        when:
        editAppointmentHandler.handle(appointmentId, command)

        then:
        thrown(AppointmentNotFoundException)
    }

    def "should throw exception when appointment not found  behavior verification"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        appointmentRepository.edit(appointmentId, command) >> { throw new AppointmentNotFoundException("Appointment not found") }

        when:
        editAppointmentHandler.handle(appointmentId, command)

        then:
        1 * appointmentRepository.edit(appointmentId, command)
    }


    def "should throw exception when appointment not found ERROR"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        appointmentRepository.edit(appointmentId, command) >> { throw new AppointmentNotFoundException("Appointment not found") }

        when:
        editAppointmentHandler.handle(appointmentId, command)

        //TAKI TEST NIE DZIAŁA
        then:
        1 * appointmentRepository.edit(appointmentId, command)
        thrown(AppointmentNotFoundException)
    }

}
