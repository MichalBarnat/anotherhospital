package com.bbc.anotherhospital.appointment

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener,
        DbUnitTestExecutionListener
])
class AppointmentIT extends Specification {

    @Autowired
    MockMvc mockMvc


    @DatabaseSetup("/appointment-data.xml")
    def "should find appointment with id 1"() {
        when: "Perform GET request"
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        then: "Expect OK status"
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }

}