package com.bbc.anotherhospital.doctor.factory;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;

public class DoctorFactory {

    public static Doctor createDoctor(CreateDoctorCommand command) {
        return null;
    }

    public static DoctorSnapshot createDoctorSnapshot(Doctor doctor) {
        return null;
    }

}
