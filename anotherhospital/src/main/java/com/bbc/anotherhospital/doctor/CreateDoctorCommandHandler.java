package com.bbc.anotherhospital.doctor;

import org.springframework.stereotype.Repository;

public interface CreateDoctorCommandHandler {
    DoctorSnapshot handle(CreateDoctorCommand command);
}

class CreateDoctorCommandHandleImpl implements CreateDoctorCommandHandler {

    @Override
    public DoctorSnapshot handle(CreateDoctorCommand command) {

//        Doctor doctorToSave = jakis konwerter moze bycmodelmapper.convert(command)
        // doctor savedDoctor = doctorrepo.save(doctorToSave);
//        return przekonwertowany doctorSnapshot
        return null;
    }
}