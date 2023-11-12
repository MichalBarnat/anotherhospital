package com.bbc.anotherhospital.patient.handlers;

import com.bbc.anotherhospital.patient.commands.CreatePatientCommand;
import com.bbc.anotherhospital.patient.repository.PatientRepository;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FindAllPatientsQueryHandler {
    List<PatientSnapshot> handle(CreatePatientCommand command);
}

@Service
@RequiredArgsConstructor
class FindAllPatientsQueryHandlerImpl implements FindAllPatientsQueryHandler{

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientSnapshot> handle(CreatePatientCommand command) {
        return patientRepository.findAll(command).stream()
                .map(app -> modelMapper.map(app, PatientSnapshot.class))
                .toList();
    }
}
