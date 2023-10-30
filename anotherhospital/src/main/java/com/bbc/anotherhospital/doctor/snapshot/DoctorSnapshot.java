package com.bbc.anotherhospital.doctor.snapshot;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DoctorSnapshot {
    Long id;
    String name;
    String surname;
    String speciality;
    String email;
    Integer rate;
    String pesel;
}
