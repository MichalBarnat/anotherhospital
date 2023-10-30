package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.repository.DoctorRepository;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface FindDoctorQueryHandler {
    DoctorSnapshot handle(Long id);
}

@Service
class FindDoctorQueryHandlerImpl implements FindDoctorQueryHandler {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FindDoctorQueryHandlerImpl(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorSnapshot handle(Long id) {
        Doctor doctor = doctorRepository.findById(id);
        return modelMapper.map(doctor, DoctorSnapshot.class);
    }
}
