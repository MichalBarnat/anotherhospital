package com.bbc.anotherhospital.doctor;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DoctorSnapshot {
    Integer id;
    String name;
    String surname;
    String speciality;
    String email;
    Integer rate;
    String pesel;
}
