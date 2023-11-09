package com.bbc.anotherhospital.doctor;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
public class Doctor {
    private Long id;
    private String name;
    private String surname;
    private String speciality;
    private String email;
    private Integer rate;
    private String pesel;
    private Boolean validInsurance;
}
