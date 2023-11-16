package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.commands.UpdateDoctorCommand;
import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface EditPartiallyCommandHandler {
    DoctorSnapshot handler(Long id, UpdateDoctorCommand command);
}

@Service
@RequiredArgsConstructor
class EditPartiallyCommandHandlerImpl implements  EditPartiallyCommandHandler{
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public DoctorSnapshot handler(Long id, UpdateDoctorCommand command) {
        Doctor doctor = doctorRepository.editPartially(id, command);
        return modelMapper.map(doctor,DoctorSnapshot.class);
    }
}
