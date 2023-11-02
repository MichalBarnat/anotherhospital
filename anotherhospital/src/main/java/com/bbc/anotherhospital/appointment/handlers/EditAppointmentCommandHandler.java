package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface EditAppointmentCommandHandler {
    AppointmentSnapshot handle(Long id, UpdateAppointmentCommand command);
}

@Service
@RequiredArgsConstructor
class EditAppointmentCommandHandlerImpl implements EditAppointmentCommandHandler {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;


    @Override
    public AppointmentSnapshot handle(Long id, UpdateAppointmentCommand command) {
        Appointment editedAppointment = appointmentRepository.edit(id, command);
        return modelMapper.map(editedAppointment, AppointmentSnapshot.class);
    }
}
