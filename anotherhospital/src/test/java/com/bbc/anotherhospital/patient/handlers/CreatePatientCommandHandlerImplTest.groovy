package com.bbc.anotherhospital.patient.handlers

import com.bbc.anotherhospital.patient.Patient
import com.bbc.anotherhospital.patient.PatientFactory
import com.bbc.anotherhospital.patient.commands.CreatePatientCommand
import com.bbc.anotherhospital.patient.repository.PatientRepository
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot
import org.modelmapper.ModelMapper
import spock.lang.Specification

class CreatePatientCommandHandlerImplTest extends Specification {
    PatientRepository patientRepository
    ModelMapper modelMapper
    CreatePatientCommandHandlerImpl createPatientCommandHandler

    def setup() {
        patientRepository = Mock()
        modelMapper = Mock()
        createPatientCommandHandler = new CreatePatientCommandHandlerImpl(patientRepository, modelMapper)
    }

    def "shuld create patient when available state verification"() {
        given:
        CreatePatientCommand command = new CreatePatientCommand(name: "PatientName", surname: "PatientSurname",

                email: "patient@gmail.com", pesel: "12345678999", validInsurance: true)
        Patient savedPatient = PatientFactory.createPatient(1L, "PatientName", "PatientSurname",
                "patient@gmail.com", "12345678999", true);
        PatientSnapshot expectedSnapshot = PatientSnapshot.builder()
                .id(savedPatient.getId())
                .name(savedPatient.getName())
                .surname(savedPatient.getSurname())
                .email(savedPatient.getEmail())
                .pesel(savedPatient.getPesel())
                .validInsurance(savedPatient.getValidInsurance())
                .build();

        patientRepository.save(command) >>> savedPatient
        modelMapper.map(savedPatient, PatientSnapshot.class) >> expectedSnapshot

        when:
        PatientSnapshot actualSnapshot = createPatientCommandHandler.handle(command)

        then:
        actualSnapshot == expectedSnapshot
    }

    def "should create appointment when available v2"() {
        given:
        CreatePatientCommand command = new CreatePatientCommand(name: "PatientName", surname: "PatientSurname",
                email: "patient@gmail.com", pesel: "12345678999", validInsurance: true)

        when:
        patientRepository.save(command) >> PatientFactory.createPatient(1L, command.getName(), command.getSurname(),
                command.getEmail(), command.getPesel(), command.getValidInsurance())
        modelMapper.map(_,PatientSnapshot) >> PatientSnapshot.builder()
            .id(1L)
            .name(command.getName())
            .surname(command.getSurname())
            .email(command.getEmail())
            .pesel(command.getPesel())
            .validInsurance(command.getValidInsurance())
            .build()

        PatientSnapshot result = createPatientCommandHandler.handle(command)

        then:
        result.id == 1L
        result.name == command.name
        result.surname == command.surname
        result.email == command.email
        result.pesel == command.pesel
        result.validInsurance == command.validInsurance
        1 * patientRepository.save(command)
    }

    def "should create patient when available behavior verification"() {
        given:
        CreatePatientCommand command = new CreatePatientCommand(name: "PatientName", surname: "PatientSurname",
                email: "patient@gmail.com", pesel: "12345678999", validInsurance: true)

        PatientSnapshot expectedSnapshot = PatientSnapshot.builder()
                .id(1L)
                .name(command.getName())
                .surname(command.getSurname())
                .email(command.getEmail())
                .pesel(command.getPesel())
                .validInsurance(command.getValidInsurance())
                .build()

        Patient savedPatient = PatientFactory.createPatient(1L, command.getName(), command.getSurname(),
                command.getEmail(), command.getPesel(), command.getValidInsurance())
        patientRepository.save(command) >> savedPatient
        modelMapper.map(savedPatient, PatientSnapshot) >> expectedSnapshot

        when:
        PatientSnapshot result = createPatientCommandHandler.handle(command)

        then:
        result == expectedSnapshot
    }

//    def "should throw exception when patient is not available"() {
//        given:
//        CreatePatientCommand command = new CreatePatientCommand(name: "PatientName", surname: "PatientSurname",
//                email: "patient@gmail.com", pesel: "12345678999", validInsurance: true)
//        Patient conflictingPatient = PatientFactory.createPatient(2L,"PatientName123", "PatientSurname123",
//              "patient123@gmail.com","33345678999",false)
//        List<Patient> conflictingPatients = [conflictingPatient]
//
//        when:
//        createPatientCommandHandler.handle(command)
//
//        then:
//        throw(PatientIsNotAvailableException)
//    }


}
