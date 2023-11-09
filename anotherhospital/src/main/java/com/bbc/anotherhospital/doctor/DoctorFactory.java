package com.bbc.anotherhospital.doctor;

import org.springframework.stereotype.Service;

@Service
public class DoctorFactory {

    public static Doctor createDoctor() {
        return new Doctor();
    }

    public static Doctor createDoctor(long id, String name, String surname, String speciality, String email, Integer rate, String pesel, boolean validInsurance) {
        return Doctor.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .speciality(speciality)
                .email(email)
                .rate(rate)
                .pesel(pesel)
                .validInsurance(validInsurance)
                .build();
    }

    public static Doctor createDoctor(String name, String surname, String speciality, String email, int rate, String pesel, boolean validInsurance) {
        return Doctor.builder()
                .name(name)
                .surname(surname)
                .speciality(speciality)
                .email(email)
                .rate(rate)
                .pesel(pesel)
                .validInsurance(validInsurance)
                .build();
    }

}
