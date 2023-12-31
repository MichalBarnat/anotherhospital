package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface FindAppointmentQueryHandler {
    AppointmentSnapshot handle(Long id);
}

@Service
@RequiredArgsConstructor
class FindAppointmentQueryHandlerImpl implements FindAppointmentQueryHandler {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentSnapshot handle(Long id) {
        Appointment appointment = appointmentRepository.findById(id);
        return modelMapper.map(appointment, AppointmentSnapshot.class);
    }
}