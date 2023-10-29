package com.bbc.anotherhospital.appointment.controller;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.handlers.CreateAppointmentCommandHandler;
import com.bbc.anotherhospital.appointment.service.AppointmentCommandService;
import com.bbc.anotherhospital.appointment.service.AppointmentQueryService;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final CreateAppointmentCommandHandler createAppointmentCommandHandler;
    private final AppointmentQueryService appointmentQueryService;
    private final AppointmentCommandService appointmentCommandService;
    private final ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<AppointmentSnapshot> save(@RequestBody CreateAppointmentCommand command) {
        Integer createdAppointmentId = appointmentCommandService.createAppointment(command);
        Appointment createdAppointment = appointmentQueryService.findById(createdAppointmentId);
        AppointmentSnapshot appointmentSnapshot = modelMapper.map(createdAppointment, AppointmentSnapshot.class);
        return new ResponseEntity<>(appointmentSnapshot, CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentSnapshot> findById(@PathVariable Integer id) {
        Appointment appointment = appointmentQueryService.findById(id);
        return ResponseEntity.ok(modelMapper.map(appointment, AppointmentSnapshot.class));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentSnapshot>> findAll() {
        List<AppointmentSnapshot> list = appointmentQueryService.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentSnapshot.class))
                .toList();
        return ResponseEntity.ok(list);
    }

}
