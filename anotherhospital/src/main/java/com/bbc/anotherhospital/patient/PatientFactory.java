package com.bbc.anotherhospital.patient;

import org.springframework.stereotype.Service;

@Service
public class PatientFactory {

    public static Patient createPatient() {
        return new Patient();
    }

    public static Patient createPatient(long id, String name, String surname, String email, String pesel, boolean validInsurance) {
        return Patient.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .validInsurance(validInsurance)
                .build();
    }

    public static Patient createPatient(String name, String surname, String email, String pesel, boolean validInsurance) {
        return Patient.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .validInsurance(validInsurance)
                .build();
    }

}
