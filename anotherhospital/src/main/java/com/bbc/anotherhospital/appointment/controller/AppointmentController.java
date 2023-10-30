package com.bbc.anotherhospital.appointment.controller;

import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.handlers.CreateAppointmentCommandHandler;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final ModelMapper modelMapper;
    private final CreateAppointmentCommandHandler commandHandler;

    @PostMapping
    public ResponseEntity<AppointmentSnapshot> save(@RequestBody CreateAppointmentCommand command) {
       AppointmentSnapshot appointmentSnapshot = commandHandler.handle(command);
       return ResponseEntity.ok(appointmentSnapshot);
    }

}
