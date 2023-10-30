package com.bbc.anotherhospital.mappings.patient;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class PatientSnapshotToPatientConverter implements Converter<PatientSnapshot, Patient> {
    @Override
    public Patient convert(MappingContext<PatientSnapshot, Patient> mappingContext) {
        PatientSnapshot patient = mappingContext.getSource();

        return Patient.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .pesel(patient.getPesel())
                .validInsurance(patient.getValidInsurance())
                .build();
    }
}
