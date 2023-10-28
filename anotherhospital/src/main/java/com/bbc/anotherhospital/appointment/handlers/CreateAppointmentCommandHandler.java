package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface CreateAppointmentCommandHandler {
    AppointmentSnapshot handle(CreateAppointmentCommand command);
}

@Service
@RequiredArgsConstructor
class CreateAppointmentCommandHandlerImpl implements  CreateAppointmentCommandHandler {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentSnapshot handle(CreateAppointmentCommand command) {
        Appointment appointmentToSave = modelMapper.map(command, Appointment.class);
        Appointment savedAppointment = appointmentRepository.save(appointmentToSave);
        return modelMapper.map(savedAppointment, AppointmentSnapshot.class);
    }
}