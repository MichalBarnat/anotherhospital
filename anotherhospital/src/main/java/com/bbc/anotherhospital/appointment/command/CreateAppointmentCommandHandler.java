package com.bbc.anotherhospital.appointment.command;

import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;

public interface CreateAppointmentCommandHandler {
    AppointmentSnapshot handle(CreateAppointmentCommand command);
}

class CreateAppointmentCommandHandleImpl implements  CreateAppointmentCommandHandler {

    @Override
    public AppointmentSnapshot handle(CreateAppointmentCommand command) {

        return null;
    }
}