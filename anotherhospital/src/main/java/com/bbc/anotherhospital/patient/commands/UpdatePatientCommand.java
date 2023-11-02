package com.bbc.anotherhospital.patient.commands;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientCommand {
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private Boolean validInsurance;


}