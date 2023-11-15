package com.bbc.anotherhospital.appointment.handlers

import com.bbc.anotherhospital.appointment.Appointment
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot
import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.doctor.DoctorFactory
import com.bbc.anotherhospital.patient.Patient
import com.bbc.anotherhospital.appointment.AppointmentFactory
import com.bbc.anotherhospital.exceptions.AppointmentIsNotAvailableException
import com.bbc.anotherhospital.patient.PatientFactory
import org.modelmapper.ModelMapper
import spock.lang.Specification

import java.time.LocalDateTime

class CreateAppointmentCommandHandlerSpec extends Specification {

    AppointmentRepository appointmentRepository
    ModelMapper modelMapper
    CreateAppointmentCommandHandlerImpl createAppointmentCommandHandler

    def setup() {
        appointmentRepository = Mock()
        modelMapper = Mock()
        createAppointmentCommandHandler = new CreateAppointmentCommandHandlerImpl(appointmentRepository, modelMapper)
    }

    def "should create appointment when available state verification"() {
        given:
        CreateAppointmentCommand command = new CreateAppointmentCommand(doctorId: 1L, patientId: 1L, dateTime: LocalDateTime.parse("2024-12-08T16:20:00.00"), price: 100.0)
        Doctor doctor = DoctorFactory.createDoctor(1L, "DoctorName", "DoctorSurname", "Speciality", "doctor@example.com", 5, "12345678901", true)
        Patient patient = PatientFactory.createPatient(1L, "PatientName", "PatientSurname", "patient@example.com", "12345678901", true)
        Appointment savedAppointment = AppointmentFactory.createAppointment(1L, doctor, patient, LocalDateTime.parse("2024-12-08T16:20:00.00"), 100.0);
        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(savedAppointment.getId())
                .doctorId(savedAppointment.getDoctor().getId())
                .patientId(savedAppointment.getPatient().getId())
                .dateTime(savedAppointment.getDateTime())
                .price(savedAppointment.getPrice())
                .build();

        appointmentRepository.findAllByDoctorId(command.getDoctorId()) >> Collections.emptyList()
        appointmentRepository.findAllByPatientId(command.getPatientId()) >> Collections.emptyList()
        appointmentRepository.save(command) >> savedAppointment
        modelMapper.map(savedAppointment, AppointmentSnapshot.class) >> expectedSnapshot

        when:
        AppointmentSnapshot actualSnapshot = createAppointmentCommandHandler.handle(command)

        then:
        actualSnapshot == expectedSnapshot
    }

    def "should create appointment when available v2"() {
        given:
        CreateAppointmentCommand command = new CreateAppointmentCommand(doctorId: 1L, patientId: 1L, dateTime: LocalDateTime.now(), price: 100.0)
        appointmentRepository.findAllByDoctorId(1L) >> []
        appointmentRepository.findAllByPatientId(1L) >> []
        appointmentRepository.save(command) >> AppointmentFactory.createAppointment(1L, DoctorFactory.createDoctor(), PatientFactory.createPatient(), command.dateTime, command.price)
        modelMapper.map(_, AppointmentSnapshot) >> AppointmentSnapshot.builder()
                .id(1L)
                .doctorId(command.getDoctorId())
                .patientId(command.getPatientId())
                .dateTime(command.getDateTime())
                .price(command.getPrice())
                .build()

        when:
        AppointmentSnapshot result = createAppointmentCommandHandler.handle(command)

        then:
        result.id == 1L
        result.doctorId == command.doctorId
        result.patientId == command.patientId
        result.price == command.getPrice()
        1 * appointmentRepository.save(command)
    }

    def "should create appointment when available behavior verification"() {
        given:
        CreateAppointmentCommand command = new CreateAppointmentCommand(doctorId: 1L, patientId: 1L, dateTime: LocalDateTime.now(), price: 100.0)

        AppointmentSnapshot expectedSnapshot = AppointmentSnapshot.builder()
                .id(1L)
                .doctorId(command.getDoctorId())
                .patientId(command.getPatientId())
                .dateTime(command.getDateTime())
                .price(command.getPrice())
                .build()

        appointmentRepository.findAllByDoctorId(1L) >> []
        appointmentRepository.findAllByPatientId(1L) >> []
        Doctor doctor = DoctorFactory.createDoctor()
        doctor.setId(1L)
        Patient patient = PatientFactory.createPatient()
        patient.setId(1L)
        Appointment savedAppointment = AppointmentFactory.createAppointment(1L, doctor, patient, command.dateTime, command.price)
        appointmentRepository.save(command) >> savedAppointment
        modelMapper.map(savedAppointment, AppointmentSnapshot) >> expectedSnapshot

        when:
        AppointmentSnapshot result = createAppointmentCommandHandler.handle(command)

        then:
        result == expectedSnapshot
//        result.id == 1L
//        result.doctorId == command.doctorId
//        result.patientId == command.patientId
//        result.price == command.getPrice()
//        1 * appointmentRepository.save(command)
//        1 * modelMapper.map(_, AppointmentSnapshot)
    }

    def "should throw exception when appointment is not available"() {
        given:
        Doctor doctor = DoctorFactory.createDoctor(1L, "DoctorName", "DoctorSurname", "Speciality", "doctor@example.com", 5, "12345678901", true)
        Patient patient = PatientFactory.createPatient(1L, "PatientName", "PatientSurname", "patient@example.com", "12345678901", true)
        CreateAppointmentCommand command = new CreateAppointmentCommand(doctorId: doctor.id, patientId: patient.id, dateTime: LocalDateTime.parse("2042-10-01T20:57:03.93"), price: 100.0)
        Appointment conflictingAppointment = AppointmentFactory.createAppointment(2L, doctor, patient, command.dateTime, 200.0)
        List<Appointment> conflictingAppointments = [conflictingAppointment]

        appointmentRepository.findAllByDoctorId(command.getDoctorId()) >> conflictingAppointments
        appointmentRepository.findAllByPatientId(command.getPatientId()) >> conflictingAppointments

        when:
        createAppointmentCommandHandler.handle(command)

        then:
        def ex = thrown(AppointmentIsNotAvailableException)
        ex.message == "Appointment is not available at: 2042-10-01T20:57:03.930"

        1 * appointmentRepository.findAllByDoctorId(command.getDoctorId()) >> {
            throw new AppointmentIsNotAvailableException("Appointment is not available at: 2042-10-01T20:57:03.930")
        }

    }

}
