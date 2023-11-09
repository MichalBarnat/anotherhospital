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

    // ALSO GOOD:
//    AppointmentRepository appointmentRepository = Mock(AppointmentRepository)
//    ModelMapper modelMapper = Mock(ModelMapper)
//    CreateAppointmentCommandHandlerImpl createAppointmentCommandHandler = new CreateAppointmentCommandHandlerImpl(appointmentRepository, modelMapper)

    AppointmentRepository appointmentRepository
    ModelMapper modelMapper
    CreateAppointmentCommandHandlerImpl createAppointmentCommandHandler

    def setup() {
        appointmentRepository = Mock()
        modelMapper = Mock()
        createAppointmentCommandHandler = new CreateAppointmentCommandHandlerImpl(appointmentRepository, modelMapper)
    }

    def "should create appointment when appointment is available"() {
        given:
        CreateAppointmentCommand command = new CreateAppointmentCommand(doctorId: 1L, patientId: 1L, dateTime: LocalDateTime.now(), price: 100.0)

        when:
        appointmentRepository.findAllByDoctorId(_) >> []
        appointmentRepository.findAllByPatientId(_) >> []
        appointmentRepository.save(_) >> AppointmentFactory.createAppointment(1L, DoctorFactory.createDoctor(), PatientFactory.createPatient(), command.dateTime, command.price)
        modelMapper.map(_, AppointmentSnapshot) >> AppointmentSnapshot.builder()
                .id(1L)
                .doctorId(command.getDoctorId())
                .patientId(command.getPatientId())
                .dateTime(command.getDateTime())
                .price(command.getPrice())
                .build()
        AppointmentSnapshot result = createAppointmentCommandHandler.handle(command)

        then: "an appointment is created"
        assert result.id == 1;
        assert result.doctorId == command.doctorId
        assert result.patientId == command.patientId
    }

    def "should throw exception when appointment is not available"() {
        given:
        Doctor doctor = DoctorFactory.createDoctor(1L, "DoctorName", "DoctorSurname", "Speciality", "doctor@example.com", 5, "12345678901", true)
        Patient patient = PatientFactory.createPatient(1L, "PatientName", "PatientSurname", "patient@example.com", "12345678901", true)
        CreateAppointmentCommand command = new CreateAppointmentCommand(doctorId: doctor.id, patientId: patient.id, dateTime: LocalDateTime.parse("2042-10-01T20:57:03.93"), price: 100.0)
        Appointment conflictingAppointment = AppointmentFactory.createAppointment(2L, doctor, patient, command.dateTime, 200.0)
        List<Appointment> conflictingAppointments = [conflictingAppointment]

        when:
        appointmentRepository.findAllByDoctorId(command.doctorId) >> conflictingAppointments
        appointmentRepository.findAllByPatientId(command.patientId) >> conflictingAppointments
        createAppointmentCommandHandler.handle(command)

        then:
        thrown(AppointmentIsNotAvailableException)
    }


}
