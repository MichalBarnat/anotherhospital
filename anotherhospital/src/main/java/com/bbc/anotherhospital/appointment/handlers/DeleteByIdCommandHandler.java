package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface DeleteByIdCommandHandler {
    void handle(Long id);
}

@Service
class DeleteByIdCommandHandlerImpl implements DeleteByIdCommandHandler {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DeleteByIdCommandHandlerImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void handle(Long id) {
        appointmentRepository.deleteById(id);
    }
}
