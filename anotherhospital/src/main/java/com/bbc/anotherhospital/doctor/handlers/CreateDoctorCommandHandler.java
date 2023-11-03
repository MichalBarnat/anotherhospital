package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface CreateDoctorCommandHandler {
    DoctorSnapshot handle(CreateDoctorCommand command);
}

@Service
@RequiredArgsConstructor
class CreateDoctorCommandHandleImpl implements CreateDoctorCommandHandler {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public DoctorSnapshot handle(CreateDoctorCommand command) {
        Doctor savedDoctor = doctorRepository.save(command);
        return modelMapper.map(savedDoctor, DoctorSnapshot.class);
    }
}