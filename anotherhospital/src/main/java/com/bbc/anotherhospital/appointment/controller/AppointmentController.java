package com.bbc.anotherhospital.appointment.controller;

import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.appointment.commands.UpdateAppointmentCommand;
import com.bbc.anotherhospital.appointment.handlers.*;
import com.bbc.anotherhospital.appointment.snapshot.AppointmentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final CreateAppointmentCommandHandler createAppointmentCommandHandler;
    private final FindAppointmentQueryHandler findAppointmentQueryHandler;
    private final FindAllAppointmentsQueryHandler findAllAppointmentsQueryHandler;
    private final DeleteByIdCommandHandler deleteByIdCommandHandler;
    private final EditAppointmentCommandHandle editAppointmentCommandHandle;

    @Autowired
    public AppointmentController(CreateAppointmentCommandHandler createAppointmentCommandHandler, FindAppointmentQueryHandler findAppointmentQueryHandler, FindAllAppointmentsQueryHandler findAllAppointmentsQueryHandler, DeleteByIdCommandHandler deleteByIdCommandHandler, EditAppointmentCommandHandle editAppointmentCommandHandle) {
        this.createAppointmentCommandHandler = createAppointmentCommandHandler;
        this.findAppointmentQueryHandler = findAppointmentQueryHandler;
        this.findAllAppointmentsQueryHandler = findAllAppointmentsQueryHandler;
        this.deleteByIdCommandHandler = deleteByIdCommandHandler;
        this.editAppointmentCommandHandle = editAppointmentCommandHandle;
    }

    @PostMapping
    public ResponseEntity<AppointmentSnapshot> save(@RequestBody CreateAppointmentCommand command) {
       AppointmentSnapshot appointmentSnapshot = createAppointmentCommandHandler.handle(command);
       return new ResponseEntity<>(appointmentSnapshot, CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentSnapshot> editAppointment(@PathVariable Long id, @RequestBody UpdateAppointmentCommand command) {
        AppointmentSnapshot appointment = editAppointmentCommandHandle.handle(id, command);
        return ResponseEntity.ok(appointment);
    }

}
