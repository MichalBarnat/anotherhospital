package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface EditPartiallyCommandHandler {
    AppointmentSnapshot handler(Long id, UpdateAppointmentCommand command);
}

@Service
@RequiredArgsConstructor
class EditPartiallyCommandHandlerImpl implements EditPartiallyCommandHandler {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentSnapshot handler(Long id, UpdateAppointmentCommand command) {
        Appointment appointment = appointmentRepository.editPartially(id, command);
        return modelMapper.map(appointment, AppointmentSnapshot.class);
    }
}