package com.bbc.anotherhospital.patient;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
public class Patient {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private Boolean validInsurance;
}