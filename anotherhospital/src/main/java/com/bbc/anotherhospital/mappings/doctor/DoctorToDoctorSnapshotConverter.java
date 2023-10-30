package com.bbc.anotherhospital.mappings.doctor;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class DoctorToDoctorSnapshotConverter implements Converter<Doctor, DoctorSnapshot> {
    @Override
    public DoctorSnapshot convert(MappingContext<Doctor, DoctorSnapshot> mappingContext) {
        Doctor doctor = mappingContext.getSource();

        return DoctorSnapshot.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .speciality(doctor.getSpeciality())
                .email(doctor.getEmail())
                .rate(doctor.getRate())
                .pesel(doctor.getPesel())
                .build();
    }
}
