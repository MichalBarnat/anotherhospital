package com.bbc.anotherhospital.doctor.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDoctorPageCommand {
    private int pageNumber = 0;
    private int pageSize = 5;
    private String sortDirection = "ASC";
    private String sortBy = "id";
}
