package com.bbc.anotherhospital.mappings.appointment;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentToAppointmentSnapshotConverter implements Converter<Appointment, AppointmentSnapshot> {

    @Override
    public AppointmentSnapshot convert(MappingContext<Appointment, AppointmentSnapshot> mappingContext) {
        Appointment appointment = mappingContext.getSource();

        return AppointmentSnapshot.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .dateTime(appointment.getDateTime())
                .price(appointment.getPrice())
                .build();
    }

}
