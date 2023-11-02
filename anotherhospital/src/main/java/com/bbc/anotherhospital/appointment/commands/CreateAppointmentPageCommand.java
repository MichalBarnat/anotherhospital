package com.bbc.anotherhospital.appointment.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAppointmentPageCommand {
    private int pageNumber = 0;
    private int pageSize = 5;
    private String sortDirection = "ASC";
    private String sortBy = "id";
}
