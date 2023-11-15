package com.bbc.anotherhospital.appointment.repository

import com.bbc.anotherhospital.TestConfig
import com.bbc.anotherhospital.appointment.commands.CreateAppointmentCommand
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import spock.lang.Specification;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfig.class)
class AppointmentControllerIntegrationSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    def "should handle CRUD operations for appointments"() {
        given: "An appointment creation request"
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
        .doctorId(1L)
        .patientId(1L)
        .dateTime(LocalDateTime.now())
        .price(100.0)
        .build()

        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        String createJson = mapper.writeValueAsString(command)

        when: "Create the appointment"
        MvcResult createResult = mockMvc.perform(
                post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson)
        ).andReturn()

        then: "The appointment is created"
        assert createResult.response.status == HttpStatus.CREATED.value()

        println createResult.response.toString()



        //Long createdId = extractIdFromResponse(createResult)

//        when: "Read the created appointment"
//        MvcResult readResult = mockMvc.perform(get("/appointment/" + createdId)).andReturn()
//
//        then: "The appointment details are correct"
//        assert readResult.response.status == HttpStatus.OK.value()
//
//        when: "Update the appointment"
//        String updateJson = new ObjectMapper().writeValueAsString(new UpdateAppointmentCommand(/*...*/))
//        MvcResult updateResult = mockMvc.perform(
//                put("/appointment/" + createdId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updateJson)
//        ).andReturn()
//
//        then: "The appointment is updated"
//        assert updateResult.response.status == HttpStatus.OK.value()
//
//        when: "Delete the appointment"
//        MvcResult deleteResult = mockMvc.perform(delete("/appointment/" + createdId)).andReturn()
//
//        then: "The appointment is deleted"
//        assert deleteResult.response.status == HttpStatus.NO_CONTENT.value()
    }

    private Long extractIdFromResponse(MvcResult result) {
        // Implementacja ekstrakcji ID z odpowiedzi, np. z JSON
    }
}