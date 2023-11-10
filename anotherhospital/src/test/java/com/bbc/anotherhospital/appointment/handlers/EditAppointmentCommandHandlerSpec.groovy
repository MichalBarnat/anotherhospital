package com.bbc.anotherhospital.appointment.handlers

import com.bbc.anotherhospital.appointment.Appointment
import com.bbc.anotherhospital.appointment.AppointmentFactory
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot
import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.doctor.DoctorFactory
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

        Appointment existingAppointment = AppointmentFactory.createAppointment()
        existingAppointment.setId(appointmentId)
        existingAppointment.setDoctor(oldDoctor)

        Appointment editedAppointment = AppointmentFactory.createAppointment()
        editedAppointment.setId(appointmentId)
        editedAppointment.setDoctor(newDoctor)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(appointmentId)
                .doctorId(newDoctorId)
                .build()

        appointmentRepository.findById(appointmentId) >> editedAppointment
        appointmentRepository.edit(appointmentId, command) >> editedAppointment
        modelMapper.map(editedAppointment, AppointmentSnapshot.class) >> expectedSnapshot

        when:
        AppointmentSnapshot actualSnapshot = editAppointmentHandler.handle(appointmentId, command)

        then:
        actualSnapshot.id == appointmentId
        actualSnapshot.doctorId == newDoctorId
        1 * appointmentRepository.edit(appointmentId, command)
        1 * modelMapper.map(editedAppointment, AppointmentSnapshot.class)
    }

}
