package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

public interface CreateAppointmentCommandHandler {
    AppointmentSnapshot handle(CreateAppointmentCommand command);
}

@Service
@RequiredArgsConstructor
class CreateAppointmentCommandHandlerImpl implements  CreateAppointmentCommandHandler {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public AppointmentSnapshot handle(CreateAppointmentCommand command) {
        return null;
    }
}