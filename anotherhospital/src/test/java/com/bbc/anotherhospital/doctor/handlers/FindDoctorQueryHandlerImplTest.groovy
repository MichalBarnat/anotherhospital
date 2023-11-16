package com.bbc.anotherhospital.doctor.handlers

import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.doctor.repository.DoctorRepository
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot
import com.bbc.anotherhospital.exceptions.DoctorNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class FindDoctorQueryHandlerImplTest extends Specification {

    @Autowired
    DoctorRepository doctorRepository = Mock()

    @Autowired
    ModelMapper modelMapper = Mock()

    FindDoctorQueryHandlerImpl service

    def setup() {
        service = new FindDoctorQueryHandlerImpl(doctorRepository, modelMapper)
    }

    def "should return DoctorSnapshot for given doctor id"() {
        given:
        Long doctorId = 1L

        Doctor doctor = Doctor.builder()
                .id(doctorId)
                .name("Jan")
                .surname("Kowalski")
                .speciality("Kardiolog")
                .email("kowalski@gmail.com")
                .rate(9)
                .pesel("8966548765")
                .validInsurance(true)
                .build()

        DoctorSnapshot expectedSnapshot = DoctorSnapshot.builder()
                .id(doctorId)
                .name("Jan")
                .surname("Kowalski")
                .speciality("Kardiolog")
                .email("kowalski@gmail.com")
                .rate(9)
                .pesel("8966548765")
                .validInsurance(true)
                .build()

        when:
        doctorRepository.findById(doctorId) >> doctor
        modelMapper.map(doctor, DoctorSnapshot.class) >> expectedSnapshot

        DoctorSnapshot result = service.handle(doctorId)

        then:
        1 * doctorRepository.findById(doctorId)
        1 * modelMapper.map(doctor, DoctorSnapshot.class)
        result == expectedSnapshot
    }

    def "should throw DoctorNotFoundException when doctor not found"() {
        given:
        Long doctorId = 2L

        when:
        doctorRepository.findById(doctorId) >> { throw new DoctorNotFoundException("Doctor with id $doctorId not found") }

        then:
        service.handle(doctorId) >> { e -> e instanceof DoctorNotFoundException }
        1 * doctorRepository.findById(doctorId)
        0 * modelMapper.map(_, _)
    }


}
