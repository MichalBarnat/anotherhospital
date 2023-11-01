package com.bbc.anotherhospital.mappings.appointment;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.factory.AppointmentFactory;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAppointmentCommandToAppointmentConverter implements Converter<CreateAppointmentCommand, Appointment> {

    private final AppointmentFactory appointmentFactory;

    @Override
    public Appointment convert(MappingContext<CreateAppointmentCommand, Appointment> mappingContext) {
        CreateAppointmentCommand command = mappingContext.getSource();

        return null;
    }
}
