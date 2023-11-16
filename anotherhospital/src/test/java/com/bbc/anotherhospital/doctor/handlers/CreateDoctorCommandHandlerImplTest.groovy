package com.bbc.anotherhospital.doctor.handlers

import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.doctor.DoctorFactory
import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand
import com.bbc.anotherhospital.doctor.repository.DoctorRepository
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot
import org.modelmapper.ModelMapper
import spock.lang.Specification

class CreateDoctorCommandHandlerImplTest extends Specification {
    DoctorRepository doctorRepository
    ModelMapper modelMapper
    CreateDoctorCommandHandlerImpl createDoctorCommandHandler

    def setup() {
        doctorRepository = Mock()
        modelMapper = Mock()
        createDoctorCommandHandler = new CreateDoctorCommandHandlerImpl(doctorRepository, modelMapper)
    }

    def "should create doctor when available state verification"() {
        given:
        CreateDoctorCommand command = new CreateDoctorCommand(
                name: "DoctorName",
                surname: "DoctorSurname",
                speciality: "DoctorSpeciality",
                email: "doctor@gmail.com",
                rate: "9",
                pesel: "12345678999",
                validInsurance: true)
        Doctor savedDoctor = DoctorFactory.createDoctor(1L,"DoctorName", "DoctorSurname", "DoctorSpeciality", "doctor@gmail.com", "9", "12345678999", true)
        DoctorSnapshot expectedSnapshot = DoctorSnapshot.builder()
        .id(savedDoctor.getId())
        .name(savedDoctor.getName())
        .surname(savedDoctor.getSurname())
        .email(savedDoctor.getEmail())
        .rate(savedDoctor.getRate())
        .pesel(savedDoctor.getPesel())
        .validInsurance(savedDoctor.getValidInsurance())
        .build();

        doctorRepository.save(command) >>> savedDoctor
        modelMapper.map(savedDoctor, DoctorSnapshot.class) >> expectedSnapshot

        when:
        DoctorSnapshot actualSnapshot = createDoctorCommandHandler.handle(command)

        then:
        actualSnapshot == expectedSnapshot
    }

    def "should create doctor when available behaviour verification"(){
        given:
        CreateDoctorCommand command = new CreateDoctorCommand(
                name: "DoctorName",
                surname: "DoctorSurname",
                speciality: "DoctorSpeciality",
                email: "doctor@gmail.com",
                rate: "9",
                pesel: "12345678999",
                validInsurance: true)

        DoctorSnapshot expectedSnapshot = DoctorSnapshot.builder()
                .id(1L)
                .name(command.getName())
                .surname(command.getSurname())
                .email(command.getEmail())
                .rate(command.getRate())
                .pesel(command.getPesel())
                .validInsurance(command.getValidInsurance())
                .build();

        Doctor savedDoctor = DoctorFactory.createDoctor(1L,"DoctorName", "DoctorSurname", "DoctorSpeciality", "doctor@gmail.com", "9", "12345678999", true)

        doctorRepository.save(command) >> savedDoctor
        modelMapper.map(savedDoctor, DoctorSnapshot) >> expectedSnapshot

        when:
        DoctorSnapshot result = createDoctorCommandHandler.handle(command)

        then:
        result == expectedSnapshot
    }
}
