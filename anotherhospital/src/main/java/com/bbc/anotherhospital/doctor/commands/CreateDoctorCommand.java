package com.bbc.anotherhospital.doctor.commands;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorCommand{
    private String name;
    private String surname;
    private String speciality;
    private String email;
    private Integer rate;
    private String pesel;
    private Boolean validInsurance;
}
