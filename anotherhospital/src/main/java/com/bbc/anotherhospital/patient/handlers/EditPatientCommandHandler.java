package com.bbc.anotherhospital.patient.handlers;

import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.commands.UpdatePatientCommand;
import com.bbc.anotherhospital.patient.repository.PatientRepository;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

public interface EditPatientCommandHandler {
    PatientSnapshot handle(Long id, UpdatePatientCommand command);
}

@Service
@RequiredArgsConstructor
class EditPatientCommandHandlerImpl implements  EditPatientCommandHandler {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    @Override
    public PatientSnapshot handle(Long id, UpdatePatientCommand command) {
        Patient editedPatient = patientRepository.edit(id,command);
        return modelMapper.map(editedPatient, PatientSnapshot.class);
    }
}

