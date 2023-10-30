package com.bbc.anotherhospital.doctor.controller;

import com.bbc.anotherhospital.doctor.handlers.FindDoctorQueryHandler;
import com.bbc.anotherhospital.doctor.snapshot.DoctorSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final FindDoctorQueryHandler findDoctorQueryHandler;

    @Autowired
    public DoctorController(FindDoctorQueryHandler findDoctorQueryHandler) {
        this.findDoctorQueryHandler = findDoctorQueryHandler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorSnapshot> getDoctorById(@PathVariable Long id) {
        DoctorSnapshot doctor = findDoctorQueryHandler.handle(id);
        return ResponseEntity.ok(doctor);
    }
}
