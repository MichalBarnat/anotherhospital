package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.commands.CreateDoctorPageCommand;
import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FindAllDoctorsQueryHandler {
    List<DoctorSnapshot> handle(CreateDoctorPageCommand command);
}

@Service
@RequiredArgsConstructor
class FindAllDoctorsQueryHandlerImpl implements FindAllDoctorsQueryHandler {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DoctorSnapshot> handle(CreateDoctorPageCommand command) {
        return doctorRepository.findAll().stream()
                .map(app -> modelMapper.map(app, DoctorSnapshot.class))
                .toList();
    }
}
