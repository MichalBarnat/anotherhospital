package com.bbc.anotherhospital.appointment.repository

import com.bbc.anotherhospital.AnotherhospitalApplication
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand
import com.bbc.anotherhospital.exceptions.AppointmentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import com.bbc.anotherhospital.appointment.Appointment
import com.bbc.anotherhospital.doctor.handlers.FindDoctorQueryHandler
import com.bbc.anotherhospital.patient.handlers.FindPatientQueryHandler
import org.modelmapper.ModelMapper

@SpringBootTest
@ContextConfiguration(classes = AnotherhospitalApplication.class)
class AppointmentRepositoryTest extends Specification {


    @Autowired
    AppointmentRepository appointmentRepository

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate

    @Autowired
    FindDoctorQueryHandler findDoctorQueryHandler

    @Autowired
    FindPatientQueryHandler findPatientQueryHandler

    @Autowired
    ModelMapper modelMapper

    def "save appointment"() {
        given:
        def command = new CreateAppointmentCommand(doctorId, patientId, LocalDateTime.now(), 100.0)

        when:
        Appointment appointment = appointmentRepository.save(command)

        then:
        appointment != null
        appointment.id != null
        appointment.price == 100.0
    }

    def "find appointment by id"() {
        given:
        def command = new CreateAppointmentCommand(doctorId, patientId, LocalDateTime.now(), 100.0)
        Appointment savedAppointment = appointmentRepository.save(command)

        when:
        Appointment foundAppointment = appointmentRepository.findById(savedAppointment.id)

        then:
        foundAppointment != null
        foundAppointment.id == savedAppointment.id
    }

    def "edit appointment"() {
        given:
        def command = new CreateAppointmentCommand(doctorId, patientId, LocalDateTime.now(), 100.0)
        Appointment savedAppointment = appointmentRepository.save(command)
        def updateCommand = new UpdateAppointmentCommand(newDoctorId, newPatientId, LocalDateTime.now().plusDays(1), 200.0)

        when:
        Appointment updatedAppointment = appointmentRepository.edit(savedAppointment.id, updateCommand)

        then:
        updatedAppointment != null
        updatedAppointment.id == savedAppointment.id
        updatedAppointment.price == 200.0
    }

    def "delete appointment"() {
        given:
        def command = new CreateAppointmentCommand(doctorId, patientId, LocalDateTime.now(), 100.0)
        Appointment savedAppointment = appointmentRepository.save(command)

        when:
        appointmentRepository.deleteById(savedAppointment.id)
        boolean thrown = false
        try {
            appointmentRepository.findById(savedAppointment.id)
        } catch (AppointmentNotFoundException e) {
            thrown = true
        }

        then:
        thrown
    }
}
