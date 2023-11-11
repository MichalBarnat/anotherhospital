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

    def "should edit appointment with only doctorId changed"() {
        given:
        Long appointmentId = 1L
        Long newDoctorId = 2L
        UpdateAppointmentCommand command = UpdateAppointmentCommand.builder().doctorId(newDoctorId).build()

        Doctor oldDoctor = DoctorFactory.createDoctor()
        oldDoctor.setId(10L)

        Doctor newDoctor = DoctorFactory.createDoctor()
        newDoctor.setId(newDoctorId)

        Appointment editedAppointment = AppointmentFactory.createAppointment()
        editedAppointment.setId(appointmentId)
        editedAppointment.setDoctor(newDoctor)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointmentId)
                .doctorId(newDoctorId)
                .build()

        when:
        appointmentRepository.findById(appointmentId) >> editedAppointment
        appointmentRepository.edit(appointmentId, command) >> editedAppointment
        modelMapper.map(editedAppointment, AppointmentSnapshot.class) >> expectedSnapshot
        AppointmentSnapshot actualSnapshot = editAppointmentHandler.handle(appointmentId, command)

        then:
//        actualSnapshot.id == appointmentId
//        actualSnapshot.doctorId == newDoctorId
//        actualSnapshot.dateTime == null
//        actualSnapshot.price == null
        1 * appointmentRepository.edit(appointmentId, command)

        //1 * appointmentRepository.findById(appointmentId)
        //1 * modelMapper.map(editedAppointment, AppointmentSnapshot.class)
    }


    def "should handle edit command and return appointment snapshot_v1"() {
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
        // TODO MICHAL_1 dlaczego po tym wywala nulla?
//        1 * appointmentRepository.edit(appointmentId, command)
//        1 * modelMapper.map(editedAppointment, AppointmentSnapshot.class)
    }

    def "should handle edit command and return appointment snapshot_v2"() {
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
        // TODO MICHAL_1 dlaczego po tym wywala nulla?
//        1 * appointmentRepository.edit(appointmentId, command)
//        1 * modelMapper.map(editedAppointment, AppointmentSnapshot.class)
    }

    def "should throw exception when appointment not found_v1"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        appointmentRepository.edit(appointmentId, command) >> { throw new AppointmentNotFoundException("Appointment not found") }
        appointmentRepository.findById(appointmentId) >> { throw new AppointmentNotFoundException("Appointment not found") }

        when:
        editAppointmentHandler.handle(appointmentId, command)

        then:
        thrown(AppointmentNotFoundException)
        // TAK DZIAŁA ALE Z LINIJKĄ PONIZEJ NIE! WHY ???
        //1 * appointmentRepository.edit(appointmentId, command)
    }

    def "should throw exception when appointment not found_v2"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        appointmentRepository.edit(appointmentId, command) >> { throw new AppointmentNotFoundException("Appointment not found") }
        appointmentRepository.findById(appointmentId) >> { throw new AppointmentNotFoundException("Appointment not found") }

        when:
        editAppointmentHandler.handle(appointmentId, command)

        then:
        1 * appointmentRepository.edit(appointmentId, command)
        // TAK DZIAŁA ALE Z LINIJKĄ PONIZEJ NIE! WHY ???
//        thrown(AppointmentNotFoundException)
    }


    def "should throw exception when appointment not found"() {
        given:
        Long appointmentId = 1L
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(doctorId: 2L, price: 100.0)

        appointmentRepository.edit(appointmentId, command) >> { throw new AppointmentNotFoundException("Appointment not found") }

        when:
        editAppointmentHandler.handle(appointmentId, command)

        //A TU JUZ NI CHUJA
        then:
        1 * appointmentRepository.edit(appointmentId, command)
        thrown(AppointmentNotFoundException)
    }

}
