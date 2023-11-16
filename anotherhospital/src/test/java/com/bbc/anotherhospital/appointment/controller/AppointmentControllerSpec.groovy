package com.bbc.anotherhospital.appointment.controller

import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand
import com.bbc.anotherhospital.appointment.handlers.CreateAppointmentCommandHandler
import com.bbc.anotherhospital.appointment.handlers.DeleteAppointmentByIdCommandHandler
import com.bbc.anotherhospital.appointment.handlers.EditAppointmentCommandHandler
import com.bbc.anotherhospital.appointment.handlers.EditPartiallyCommandHandler
import com.bbc.anotherhospital.appointment.handlers.FindAllAppointmentsQueryHandler
import com.bbc.anotherhospital.appointment.handlers.FindAppointmentQueryHandler
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
class AppointmentControllerSpec extends Specification {

    CreateAppointmentCommandHandler createAppointmentCommandHandler = Mock()
    FindAppointmentQueryHandler findAppointmentQueryHandler = Mock()
    FindAllAppointmentsQueryHandler findAllAppointmentsQueryHandler = Mock()
    DeleteAppointmentByIdCommandHandler deleteAppointmentByIdCommandHandler = Mock()
    EditAppointmentCommandHandler editAppointmentCommandHandler = Mock()
    EditPartiallyCommandHandler editPartiallyCommandHandler = Mock()

    AppointmentController controller = new AppointmentController(createAppointmentCommandHandler, findAppointmentQueryHandler, findAllAppointmentsQueryHandler, deleteAppointmentByIdCommandHandler, editAppointmentCommandHandler, editPartiallyCommandHandler)


    def "save appointment"() {
        given:
        def command = new CreateAppointmentCommand(1L, 1L, LocalDateTime.now(), 200.0)

        when:
        ResponseEntity<AppointmentSnapshot> response = controller.save(command)

        then:
        response.statusCode == HttpStatus.CREATED
        1 * createAppointmentCommandHandler.handle(command)
        //response.body != null
    }

    def "get appointment by id"() {
        given:
        Long id = 1L

        when:
        ResponseEntity<AppointmentSnapshot> response = controller.findById(id)

        then:
        response.statusCode == HttpStatus.OK
        1 * findAppointmentQueryHandler.handle(id)
        //response.body.id == id
    }

    def "delete appointment"() {
        given:
        Long id = 1L

        when:
        ResponseEntity<Void> response = controller.deleteById(id)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "edit appointment"() {
        given:
        Long id = 1L
        UpdateAppointmentCommand command = UpdateAppointmentCommand.builder()
        .doctorId(1L)
        .build()

        when:
        ResponseEntity<AppointmentSnapshot> response = controller.editAppointment(id, command)

        then:
        response.statusCode == HttpStatus.OK
        1 * editAppointmentCommandHandler.handle(1L, command)
        //response.body.price == 300.0
    }

}
