package com.bbc.anotherhospital.mappings

import com.bbc.anotherhospital.doctor.Doctor
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot
import com.bbc.anotherhospital.mappings.doctor.DoctorToDoctorSnapshotConverter
import org.modelmapper.spi.MappingContext
import spock.lang.Specification

class DoctorToDoctorSnapshotConverterTest extends Specification {

    def "should convert Doctor to DoctorSnapshot correctly"(){
        given:
        DoctorToDoctorSnapshotConverter converter = new DoctorToDoctorSnapshotConverter();
        Doctor doctor = new Doctor(
                id: 1L,
                name: "Jan",
                surname: "Kowalski",
                speciality: "Kardiolog",
                email: "jan.kowalski@email.com",
                rate: 9,
                pesel: 90010112341,
                validInsurance: true
        )

        MappingContext<Doctor, DoctorSnapshot> context = Mock(MappingContext)
        context.getSource() >> doctor

        when:
        DoctorSnapshot result = converter.convert(context)

        then:
        with(result){
            id == doctor.id
            name == doctor.name
            surname == doctor.surname
            speciality == doctor.speciality
            email == doctor.email
            rate == doctor.rate
            pesel == doctor.pesel
            validInsurance == doctor.validInsurance
        }
    }
}
