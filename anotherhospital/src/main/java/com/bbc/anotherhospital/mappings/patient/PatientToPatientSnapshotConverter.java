package com.bbc.anotherhospital.mappings.patient;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class PatientToPatientSnapshotConverter implements Converter<Patient, PatientSnapshot> {
    @Override
    public PatientSnapshot convert(MappingContext<Patient, PatientSnapshot> mappingContext) {
        Patient patient = mappingContext.getSource();

        return PatientSnapshot.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .pesel(patient.getPesel())
                .validInsurance(patient.isValidInsurance())
                .build();
    }
}
