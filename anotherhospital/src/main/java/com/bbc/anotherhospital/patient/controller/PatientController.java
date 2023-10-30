package com.bbc.anotherhospital.patient.controller;

import com.bbc.anotherhospital.patient.handlers.FindPatientQueryHandler;
import com.bbc.anotherhospital.patient.snapshot.PatientSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final FindPatientQueryHandler findPatientQueryHandler;

    @Autowired
    public PatientController(FindPatientQueryHandler findPatientQueryHandler) {
        this.findPatientQueryHandler = findPatientQueryHandler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientSnapshot> getPatientById(@PathVariable Long id) {
        PatientSnapshot patient = findPatientQueryHandler.handle(id);
        return ResponseEntity.ok(patient);
    }
}
