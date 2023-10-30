package com.bbc.anotherhospital.mappings.doctor;

import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class DoctorSnapshotToDoctorConverter implements Converter<DoctorSnapshot, Doctor> {
    @Override
    public Doctor convert(MappingContext<DoctorSnapshot, Doctor> mappingContext) {
        DoctorSnapshot doctor = mappingContext.getSource();

        return Doctor.builder()
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
