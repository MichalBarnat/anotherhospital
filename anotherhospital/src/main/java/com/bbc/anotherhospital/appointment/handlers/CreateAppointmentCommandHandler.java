package com.bbc.anotherhospital.appointment.handlers;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.repository.AppointmentRepository;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import com.bbc.anotherhospital.exceptions.AppointmentIsNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        Appointment savedAppointment;
        if(appointmentIsAvailable(command)) {
            savedAppointment = appointmentRepository.save(command);
            return modelMapper.map(savedAppointment, AppointmentSnapshot.class);
        } else {
            throw new AppointmentIsNotAvailableException("Appointment is not available at: " + command.getDateTime());
        }
    }

    public boolean appointmentIsAvailable(CreateAppointmentCommand command) {
        LocalDateTime proposedDateTime = command.getDateTime();
        LocalDateTime proposedEndDateTime = proposedDateTime.plusMinutes(15);

        List<Appointment> doctorAppointments = appointmentRepository.findAllByDoctorId(command.getDoctorId());
        List<Appointment> patientAppointments = appointmentRepository.findAllByPatientId(command.getPatientId());

        boolean doctorTimeValidation = doctorAppointments.stream()
                .anyMatch(appointment -> {
                    LocalDateTime appointmentStart = appointment.getDateTime();
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(15);
                    return (proposedDateTime.isBefore(appointmentEnd) && proposedEndDateTime.isAfter(appointmentStart));
                });

        boolean patientTimeValidation = patientAppointments.stream()
                .anyMatch(appointment -> {
                    LocalDateTime appointmentStart = appointment.getDateTime();
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(15);
                    return (proposedDateTime.isBefore(appointmentEnd) && proposedEndDateTime.isAfter(appointmentStart));
                });

        return !doctorTimeValidation && !patientTimeValidation;
    }
}

