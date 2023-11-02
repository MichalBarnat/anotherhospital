package com.bbc.anotherhospital.patient.handlers;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.commands.CreatePatientCommand;
import com.bbc.anotherhospital.patient.repository.PatientRepository;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface CreatePatientCommandHandler {
    PatientSnapshot handle(CreatePatientCommand command);
}

@Service
@RequiredArgsConstructor
class CreatePatientCommandHandlerImpl implements CreatePatientCommandHandler {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public PatientSnapshot handle(CreatePatientCommand command) {
        Patient savedPatient = patientRepository.save(command);
        return modelMapper.map(savedPatient, PatientSnapshot.class);
    }
}
