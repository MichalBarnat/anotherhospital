package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface DeleteDoctorByIdCommandHandler {
    void handle(Long id);
}

@Service
@RequiredArgsConstructor
class DeleteDoctorByIdCommandHandlerImpl implements DeleteDoctorByIdCommandHandler {
    private final DoctorRepository doctorRepository;

    @Override
    public void handle(Long id) {
        doctorRepository.deleteById(id);
    }
}