package com.bbc.anotherhospital.mappings.patient;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.PatientFactory;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class PatientSnapshotToPatientConverter implements Converter<PatientSnapshot, Patient> {
    @Override
    public Patient convert(MappingContext<PatientSnapshot, Patient> mappingContext) {
        PatientSnapshot patient = mappingContext.getSource();

        Patient createdPatient = PatientFactory.createPatient();
        createdPatient.setId(patient.getId());
        createdPatient.setName(patient.getName());
        createdPatient.setSurname(patient.getSurname());
        createdPatient.setEmail(patient.getEmail());
        createdPatient.setPesel(patient.getPesel());
        createdPatient.setValidInsurance(patient.getValidInsurance());

        return createdPatient;
    }
}
