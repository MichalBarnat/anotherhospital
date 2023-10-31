package com.bbc.anotherhospital.appointment.controller;

import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.handlers.CreateAppointmentCommandHandler;
import com.bbc.anotherhospital.appointment.handlers.DeleteByIdCommandHandler;
import com.bbc.anotherhospital.appointment.handlers.FindAllAppointmentsQueryHandler;
import com.bbc.anotherhospital.appointment.handlers.FindAppointmentQueryHandler;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final CreateAppointmentCommandHandler commandHandler;
    private final FindAppointmentQueryHandler findAppointmentQueryHandler;
    private final FindAllAppointmentsQueryHandler findAllAppointmentsQueryHandler;
    private final DeleteByIdCommandHandler deleteByIdCommandHandler;

    @Autowired
    public AppointmentController(CreateAppointmentCommandHandler commandHandler, FindAppointmentQueryHandler findAppointmentQueryHandler, FindAllAppointmentsQueryHandler findAllAppointmentsQueryHandler, DeleteByIdCommandHandler deleteByIdCommandHandler) {
        this.commandHandler = commandHandler;
        this.findAppointmentQueryHandler = findAppointmentQueryHandler;
        this.findAllAppointmentsQueryHandler = findAllAppointmentsQueryHandler;
        this.deleteByIdCommandHandler = deleteByIdCommandHandler;
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

    @GetMapping
    public ResponseEntity<List<AppointmentSnapshot>> getAllAppointments() {
        List<AppointmentSnapshot> list = findAllAppointmentsQueryHandler.handle();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        deleteByIdCommandHandler.handle(id);
        return ResponseEntity.noContent().build();
    }

}
