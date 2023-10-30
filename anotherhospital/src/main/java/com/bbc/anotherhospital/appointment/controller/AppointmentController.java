package com.bbc.anotherhospital.appointment.controller;

import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.handlers.CreateAppointmentCommandHandler;
import com.bbc.anotherhospital.appointment.handlers.FindAppointmentQueryHandler;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final CreateAppointmentCommandHandler commandHandler;
    private final FindAppointmentQueryHandler findAppointmentQueryHandler;

    @Autowired
    public AppointmentController(CreateAppointmentCommandHandler commandHandler, FindAppointmentQueryHandler findAppointmentQueryHandler) {
        this.commandHandler = commandHandler;
        this.findAppointmentQueryHandler = findAppointmentQueryHandler;
    }

    @PostMapping
    public ResponseEntity<AppointmentSnapshot> save(@RequestBody CreateAppointmentCommand command) {
       AppointmentSnapshot appointmentSnapshot = commandHandler.handle(command);
       return ResponseEntity.ok(appointmentSnapshot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentSnapshot> getAppointmentById(@PathVariable Long id) {
        AppointmentSnapshot appointment = findAppointmentQueryHandler.handle(id);
        return ResponseEntity.ok(appointment);
    }

}
