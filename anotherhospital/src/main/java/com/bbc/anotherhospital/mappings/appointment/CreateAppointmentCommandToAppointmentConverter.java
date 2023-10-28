package com.bbc.anotherhospital.mappings.appointment;

import com.bbc.anotherhospital.appointment.Appointment;
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand;
import com.bbc.anotherhospital.doctor.Doctor;
import com.bbc.anotherhospital.doctor.service.DoctorQueryService;
import com.bbc.anotherhospital.patient.Patient;
import com.bbc.anotherhospital.patient.service.PatientQueryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAppointmentCommandToAppointmentConverter implements Converter<CreateAppointmentCommand, Appointment> {

    private final DoctorQueryService doctorQueryService;
    private final PatientQueryService patientQueryService;

    @Override
    public Appointment convert(MappingContext<CreateAppointmentCommand, Appointment> mappingContext) {
        CreateAppointmentCommand command = mappingContext.getSource();

        Doctor doctor = doctorQueryService.findById(command.getDoctorId());
        Patient patient = patientQueryService.findById(command.getPatientId());

        return Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .dateTime(command.getDateTime())
                .price(command.getPrice())
                .build();
    }
}
