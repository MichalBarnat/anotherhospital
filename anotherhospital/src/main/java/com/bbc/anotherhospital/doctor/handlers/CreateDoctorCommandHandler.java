package com.bbc.anotherhospital.doctor.handlers;

import com.bbc.anotherhospital.doctor.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;

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