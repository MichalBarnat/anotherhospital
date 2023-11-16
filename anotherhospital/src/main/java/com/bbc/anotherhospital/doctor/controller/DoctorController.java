package com.bbc.anotherhospital.doctor.controller;

import com.bbc.anotherhospital.doctor.commands.CreateDoctorCommand;
import com.bbc.anotherhospital.doctor.commands.CreateDoctorPageCommand;
import com.bbc.anotherhospital.doctor.commands.UpdateDoctorCommand;
import com.bbc.anotherhospital.doctor.handlers.*;
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
    private final FindAllDoctorsQueryHandler findAllDoctorsQueryHandler;
    private final DeleteDoctorByIdCommandHandler deleteDoctorByIdCommandHandler;
    private final EditDoctorCommandHandler editDoctorCommandHandler;
    private final EditDoctorPartiallyCommandHandler editDoctorPartiallyCommandHandler;

    @Autowired
    public DoctorController(FindDoctorQueryHandler findDoctorQueryHandler,EditDoctorPartiallyCommandHandler editPartiallyCommandHandler ,EditDoctorCommandHandler editDoctorCommandHandler ,DeleteDoctorByIdCommandHandler deleteDoctorByIdCommandHandler ,FindAllDoctorsQueryHandler findAllDoctorsQueryHandler, CreateDoctorCommandHandler createDoctorCommandHandler) {
        this.findDoctorQueryHandler = findDoctorQueryHandler;
        this.createDoctorCommandHandler = createDoctorCommandHandler;
        this.findAllDoctorsQueryHandler = findAllDoctorsQueryHandler;
        this.deleteDoctorByIdCommandHandler = deleteDoctorByIdCommandHandler;
        this.editDoctorCommandHandler = editDoctorCommandHandler;
        this.editDoctorPartiallyCommandHandler = editPartiallyCommandHandler;
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

    @GetMapping
    public ResponseEntity<List<DoctorSnapshot>> findAll(CreateDoctorPageCommand command){
        List<DoctorSnapshot> doctors = findAllDoctorsQueryHandler.handle(command);
        return ResponseEntity.ok(doctors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        deleteDoctorByIdCommandHandler.handle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorSnapshot> editDoctor(@PathVariable Long id, @RequestBody UpdateDoctorCommand command){
        DoctorSnapshot doctorSnapshot = editDoctorCommandHandler.handle(id, command);
        return ResponseEntity.ok(doctorSnapshot);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorSnapshot> editPartially(@PathVariable Long id, @RequestBody UpdateDoctorCommand command){
        DoctorSnapshot doctorSnapshot = editDoctorPartiallyCommandHandler.handler(id, command);
        return ResponseEntity.ok(doctorSnapshot);
    }

}
