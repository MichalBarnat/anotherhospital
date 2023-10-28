package com.bbc.anotherhospital.patient;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PatientSnapshot {
    Integer id;
    String name;
    String surname;
    String email;
    String pesel;
    Boolean validInsurance;
}
