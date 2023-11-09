package com.bbc.anotherhospital.mappings.doctor;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.DoctorFactory;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class DoctorSnapshotToDoctorConverter implements Converter<DoctorSnapshot, Doctor> {
    @Override
    public Doctor convert(MappingContext<DoctorSnapshot, Doctor> mappingContext) {
        DoctorSnapshot doctor = mappingContext.getSource();

        Doctor createdDoctor = DoctorFactory.createDoctor();
        createdDoctor.setId(doctor.getId());
        createdDoctor.setName(doctor.getName());
        createdDoctor.setSurname(doctor.getSurname());
        createdDoctor.setSpeciality(doctor.getSpeciality());
        createdDoctor.setEmail(doctor.getEmail());
        createdDoctor.setRate(doctor.getRate());
        createdDoctor.setPesel(doctor.getPesel());

        return createdDoctor;
    }

}
