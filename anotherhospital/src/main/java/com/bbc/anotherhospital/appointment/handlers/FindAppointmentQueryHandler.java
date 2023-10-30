package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface FindAppointmentQueryHandler {
    AppointmentSnapshot handle(Long id);
}

@Service
@RequiredArgsConstructor
class FindAppointmentQueryHandlerImpl implements FindAppointmentQueryHandler{

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentSnapshot handle(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (!appointmentOptional.isPresent()) {
            // Możesz rzucić wyjątek lub zwrócić odpowiedź HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }
        Appointment appointment = appointmentOptional.get();
        return modelMapper.map(appointment, AppointmentSnapshot.class);
    }

}