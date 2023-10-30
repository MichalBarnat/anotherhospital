package com.bbc.anotherhospital.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    private Long id;
    private String name;
    private String surname;
    private String speciality;
    private String email;
    private Integer rate;
    private String pesel;
}
