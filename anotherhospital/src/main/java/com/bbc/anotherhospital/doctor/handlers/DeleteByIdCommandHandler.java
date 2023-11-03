package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface DeleteByIdCommandHandler {
    void handle(Long id);
}

@Service
@RequiredArgsConstructor
class DeleteByIdCommandHandlerImpl implements DeleteByIdCommandHandler {
    private final DoctorRepository doctorRepository;

    @Override
    public void handle(Long id) {
        doctorRepository.deleteById(id);
    }
}