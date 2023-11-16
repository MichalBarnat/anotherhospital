package com.bbc.anotherhospital.appointment.controller

import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AppointmentIT extends Specification {

    @Autowired
    MockMvc mockMvc

    def "Test GET Request"() {
        when: "Perform GET request"
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))

        then: "Expect OK status"
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }
}