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

    def "shuld create patient when available state verification"(){
        given:
        CreatePatientCommand command = new CreatePatientCommand(name: "PatientName", surname: "PatientSurname",
        email: "patient@gmail.com", pesel: "12345678999", validInsurance: true)
        Patient savedPatient = PatientFactory.createPatient(1L,"PatientName", "PatientSurname",
                "patient@gmail.com", "12345678999",  true);
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
}
