package com.bbc.anotherhospital.appointment.controller;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.handlers.CreateAppointmentCommandHandler;
import com.bbc.anotherhospital.appointment.service.AppointmentQueryService;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final CreateAppointmentCommandHandler createAppointmentCommandHandler;
    private final AppointmentQueryService appointmentQueryService;

    @PostMapping
    public ResponseEntity<AppointmentSnapshot> save(@RequestBody CreateAppointmentCommand command) {
        AppointmentSnapshot appointment = createAppointmentCommandHandler.handle(command);
        return new ResponseEntity<>(appointment, CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        Appointment appointment = appointmentQueryService.findById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String s = "test";
        return ResponseEntity.ok(s);
    }
}
