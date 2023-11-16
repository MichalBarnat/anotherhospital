package com.bbc.anotherhospital.mappings

import com.bbc.anotherhospital.patient.Patient
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot
import spock.lang.Specification
import org.modelmapper.spi.MappingContext

class PatientToPatientSnapshotConverterTest extends Specification {

    def "should convert Patient to PatientSnapshot correctly"() {
        given:
        PatientToPatientSnapshotConverter converter = new PatientToPatientSnapshotConverter()
        Patient patient = new Patient(
                id: 1L,
                name: "Jan",
                surname: "Kowalski",
                email: "jan.kowalski@example.com",
                pesel: "12345678901",
                validInsurance: true
        )
        MappingContext<Patient, PatientSnapshot> context = Mock(MappingContext)
        context.getSource() >> patient

        when:
        PatientSnapshot result = converter.convert(context)

        then:
        with(result) {
            id == patient.id
            name == patient.name
            surname == patient.surname
            email == patient.email
            pesel == patient.pesel
            validInsurance == patient.validInsurance
        }
    }
}
