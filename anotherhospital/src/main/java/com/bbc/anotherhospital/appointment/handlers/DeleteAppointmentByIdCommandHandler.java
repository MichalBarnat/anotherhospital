package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface DeleteAppointmentByIdCommandHandler {
    void handle(Long id);
}

@Service
@RequiredArgsConstructor
class DeleteAppointmentByIdCommandHandlerImpl implements DeleteAppointmentByIdCommandHandler {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void handle(Long id) {
        appointmentRepository.deleteById(id);
    }
}
