package com.bbc.anotherhospital.patient.handlers;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.repository.PatientRepository;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface FindPatientQueryHandler {
    PatientSnapshot handle(Long id);
}

@Service
class FindPatientQueryHandlerImpl implements FindPatientQueryHandler {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FindPatientQueryHandlerImpl(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PatientSnapshot handle(Long id) {
        Patient patient = patientRepository.findById(id);
        return modelMapper.map(patient, PatientSnapshot.class);
    }
}
