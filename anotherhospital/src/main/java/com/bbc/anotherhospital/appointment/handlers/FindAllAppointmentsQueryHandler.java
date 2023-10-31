package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FindAllAppointmentsQueryHandler {
    List<AppointmentSnapshot> handle();
}

@Service
class FindAllAppointmentsQueryHandlerIml implements FindAllAppointmentsQueryHandler {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FindAllAppointmentsQueryHandlerIml(AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AppointmentSnapshot> handle() {
        return appointmentRepository.findAll().stream()
                .map(app -> modelMapper.map(app, AppointmentSnapshot.class))
                .toList();
    }
}
