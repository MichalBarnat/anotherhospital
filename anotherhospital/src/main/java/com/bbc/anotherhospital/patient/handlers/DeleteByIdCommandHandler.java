package com.bbc.anotherhospital.patient.handlers;

import com.bbc.anotherhospital.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface DeleteByIdCommandHandler {
    void handle(Long id);
}

@Service
@RequiredArgsConstructor
class DeleteByIdCommandHandlerImpl implements DeleteByIdCommandHandler {
    private final PatientRepository patientRepository;

    @Override
    public void handle(Long id) { patientRepository.deleteById(id); }
}