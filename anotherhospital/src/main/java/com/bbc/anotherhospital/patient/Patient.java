package com.bbc.anotherhospital.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private boolean validInsurance;
}
