package com.bbc.anotherhospital.doctor.controller;

import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.handlers.CreateDoctorCommandHandler;
import com.bbc.anotherhospital.doctor.handlers.FindDoctorQueryHandler;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final FindDoctorQueryHandler findDoctorQueryHandler;
    private final CreateDoctorCommandHandler createDoctorCommandHandler;

    @Autowired
    public DoctorController(FindDoctorQueryHandler findDoctorQueryHandler, CreateDoctorCommandHandler createDoctorCommandHandler) {
        this.findDoctorQueryHandler = findDoctorQueryHandler;
        this.createDoctorCommandHandler = createDoctorCommandHandler;
    }

    @PostMapping
    public ResponseEntity<DoctorSnapshot> save(@RequestBody CreateDoctorCommand command) {
        DoctorSnapshot doctorSnapshot = createDoctorCommandHandler.handle(command);
        return new ResponseEntity<>(doctorSnapshot, CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorSnapshot> getDoctorById(@PathVariable Long id) {
        DoctorSnapshot doctor = findDoctorQueryHandler.handle(id);
        return ResponseEntity.ok(doctor);
    }


}
