package com.bbc.anotherhospital.patient.snapshot;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PatientSnapshot {
    Long id;
    String name;
    String surname;
    String email;
    String pesel;
    Boolean validInsurance;
}
