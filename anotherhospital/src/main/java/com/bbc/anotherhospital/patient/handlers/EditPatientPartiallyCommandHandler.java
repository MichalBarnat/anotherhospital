package com.bbc.anotherhospital.patient.handlers;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.commands.UpdatePatientCommand;
import com.bbc.anotherhospital.patient.repository.PatientRepository;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface EditPatientPartiallyCommandHandler {
    PatientSnapshot handler(Long id, UpdatePatientCommand command);
}
@Service
@RequiredArgsConstructor
class EditPatientPartiallyCommandHandlerImpl implements EditPatientPartiallyCommandHandler {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public PatientSnapshot handler(Long id, UpdatePatientCommand command) {
        Patient patient = patientRepository.editPatrially(id, command);
        return modelMapper.map(patient, PatientSnapshot.class);
    }
}