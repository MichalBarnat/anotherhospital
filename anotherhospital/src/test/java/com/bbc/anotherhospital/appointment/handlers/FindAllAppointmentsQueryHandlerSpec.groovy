package com.bbc.anotherhospital.appointment.handlers

import com.bbc.anotherhospital.appointment.Appointment
import com.bbc.anotherhospital.appointment.AppointmentFactory
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentPageCommand
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot
import org.modelmapper.ModelMapper
import spock.lang.Specification


class FindAllAppointmentsQueryHandlerSpec extends Specification {

    AppointmentRepository appointmentRepository = Mock(AppointmentRepository)
    ModelMapper modelMapper = Mock(ModelMapper)
    FindAllAppointmentsQueryHandlerIml findAllAppointmentsQueryHandler = new FindAllAppointmentsQueryHandlerIml(appointmentRepository, modelMapper)

    def "should return list of appointments"() {
        given:
        Appointment app1 = AppointmentFactory.createAppointment(1L, null, null, null, 100.0)
        Appointment app2 = AppointmentFactory.createAppointment(2L, null, null, null, 200.0)
        List<Appointment> savedAppointments = [app1, app2]

        AppointmentSnapshot snap1 = AppointmentSnapshot.builder()
                .id(1L)
                .price(100.0)
                .build()
        AppointmentSnapshot snap2 = AppointmentSnapshot.builder()
                .id(2L)
                .price(200.0)
                .build()
        List<AppointmentSnapshot> expectedSnapshots = [snap1, snap2]

        CreateAppointmentPageCommand command = new CreateAppointmentPageCommand()
        command.setPageNumber(0)
        command.setPageSize(5)
        command.setSortBy("id")
        command.setSortDirection("ASC")

        appointmentRepository.findAll(command) >> savedAppointments
        modelMapper.map(app1, AppointmentSnapshot) >> snap1
        modelMapper.map(app2, AppointmentSnapshot) >> snap2

        when:
        List<AppointmentSnapshot> actualSnapshots = findAllAppointmentsQueryHandler.handle(command)

        then:
        actualSnapshots == expectedSnapshots
//        1 * appointmentRepository.findAll(command)
//        1 * modelMapper.map(app1, AppointmentSnapshot)
//        1 * modelMapper.map(app2, AppointmentSnapshot)
    }

    def "should return list of appointments 2"() {
        given:
        Appointment app1 = AppointmentFactory.createAppointment(1L, null, null, null, 100.0)
        Appointment app2 = AppointmentFactory.createAppointment(2L, null, null, null, 200.0)
        List<Appointment> savedAppointments = [app1, app2]

        AppointmentSnapshot snap1 = AppointmentSnapshot.builder()
                .id(1L)
                .price(100.0)
                .build()
        AppointmentSnapshot snap2 = AppointmentSnapshot.builder()
                .id(2L)
                .price(200.0)
                .build()
        List<AppointmentSnapshot> expectedSnapshots = [snap1, snap2]

        CreateAppointmentPageCommand command = new CreateAppointmentPageCommand()
        command.setPageNumber(0)
        command.setPageSize(5)

        appointmentRepository.findAll(command) >> savedAppointments
        modelMapper.map(app1, AppointmentSnapshot) >> snap1
        modelMapper.map(app2, AppointmentSnapshot) >> snap2

        when:
        List<AppointmentSnapshot> actualSnapshots = findAllAppointmentsQueryHandler.handle(command)

        then:
        actualSnapshots == expectedSnapshots
    }

}