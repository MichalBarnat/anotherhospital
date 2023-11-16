package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.commands.UpdateDoctorCommand;
import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface EditDoctorCommandHandler {
    DoctorSnapshot handle(Long id, UpdateDoctorCommand command);
}

@Service
@RequiredArgsConstructor
class EditDoctorCommandHandlerImpl implements EditDoctorCommandHandler {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public DoctorSnapshot handle(Long id, UpdateDoctorCommand command) {
        Doctor editedDoctor = doctorRepository.edit(id, command);
        return modelMapper.map(editedDoctor, DoctorSnapshot.class);
    }
}