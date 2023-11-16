package com.bbc.anotherhospital.appointment

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.http.MediaType
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

    @DatabaseSetup("/sample-data.xml")
    def "should find appointment with id 1"() {
        when: "Perform GET request"
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        then: "Expect OK status"
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    @DatabaseSetup("/sample-data.xml")
    def "should find appointment with id 20"() {
        when: "Perform GET request"
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/20"))
                .andDo(MockMvcResultHandlers.print())

        then: "Expect OK status and correct values"
        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(19L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-20T16:15:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(125.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should not find appointment with id 21 and show error message"() {
        when: "Perform GET request"
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/21"))
                .andDo(MockMvcResultHandlers.print())

        then: "expect not found status and custom error message"
        result
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Appointment with id 21 not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("uri").value("/appointment/21"))
                .andExpect(MockMvcResultMatchers.jsonPath("method").value("GET"))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should create a new appointment"() {
        given: "A valid CreateAppointmentCommand"
        def requestJson = """
        {
            "doctorId": 1,
            "patientId": 2,
            "dateTime": "2013-01-10T14:30:00",
            "price": 120.0
        }
        """

        when: "Perform POST request"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(21L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2013-01-10T14:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(120.0))

        then: "Expect a new appointment to be created"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/21"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(21L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2013-01-10T14:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(120.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should not create a new appointment if doctor have smth on that time"() {
        given: "A valid CreateAppointmentCommand"
        def requestJson = """
        {
    "doctorId" : "1",
    "patientId" : "18",
    "dateTime" : "2023-01-01 10:00:00",
    "price" : "420.2"
        }
        """

        when: "Perform POST request"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())

        then: "Expect a new appointment to be created"
        result
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    @DatabaseSetup("/sample-data.xml")
    def "should not create a new appointment if patient have smth on that time"() {
        given: "A valid CreateAppointmentCommand"
        def requestJson = """
        {
    "doctorId" : "12",
    "patientId" : "5",
    "dateTime" : "2023-01-05 16:00:00",
    "price" : "420.2"
        }
        """

        when: "Perform POST request"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())

        then: "Expect a new appointment to be created"
        result
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    @DatabaseSetup("/sample-data.xml")
    def "should delete appointment with id 1"() {
        when: "Perform DELETE request"
        def result = mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())

        then: "Expect appointment with id 1 to be deleted"
        result.andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    @DatabaseSetup("/sample-data.xml")
    def "should not delete appointment that do not exist"() {
        when: "Perform DELETE request"
        def result = mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/100"))
                .andDo(MockMvcResultHandlers.print())

        then: "Expect appointment with id 1 to be deleted"
        result
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Appointment with id 100 not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("uri").value("/appointment/100"))
                .andExpect(MockMvcResultMatchers.jsonPath("method").value("DELETE"))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit all data in appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "doctorId": 20,
            "patientId": 20,
            "dateTime": "2050-01-15T16:00:00",
            "price": 15000.0
        }
        """

        when: "Perform PUT request"
        def result = mockMvc.perform(MockMvcRequestBuilders.put("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2050-01-15T16:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(15000.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2050-01-15T16:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(15000.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit only doctorId - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "doctorId": 20
        }
        """

        when: "Perform PUT request"
        def result = mockMvc.perform(MockMvcRequestBuilders.put("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(0.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(0.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit only patientId - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "patientId": 5
        }
        """

        when: "Perform PUT request"
        def result = mockMvc.perform(MockMvcRequestBuilders.put("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(0.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(0.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit only dateTime - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "dateTime": "2011-01-01T10:00:00"
        }
        """

        when: "Perform PUT request"
        def result = mockMvc.perform(MockMvcRequestBuilders.put("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2011-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(0.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2011-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(0.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit only price - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "price": "420.69"
        }
        """

        when: "Perform PUT request"
        def result = mockMvc.perform(MockMvcRequestBuilders.put("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(420.69))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value(null))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(420.69))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit partially doctorId - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "doctorId": 5
        }
        """

        when: "Perform PATCH request"
        def result = mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(5L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(5L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit partially patientId - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "patientId": 18
        }
        """

        when: "Perform PATCH request"
        def result = mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(18L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(18L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit partially doctorId and patientId - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "doctorId": 12,
            "patientId": 18
        }
        """

        when: "Perform PATCH request"
        def result = mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(12L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(18L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(12L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(18L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit partially doctorId and dateTime - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "doctorId": 12,
            "dateTime" :"2040-01-01T10:00:00"
        }
        """

        when: "Perform PATCH request"
        def result = mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(12L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2040-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(12L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2040-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))
    }

    @DatabaseSetup("/sample-data.xml")
    def "should edit partially dateTime and price - appointment with id 1"() {
        given: "check data before edit and create valid UpdateAppointmentCommand"

        def beforeEdit = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2023-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(105.0))

        def requestJson = """
        {
            "dateTime" :"2040-01-01T10:00:00",
            "price" : 5.2
        }
        """

        when: "Perform PATCH request"
        def result = mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2040-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(5.2))

        then: "Expect appointment with id 1 to be updated"
        def check = mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("doctorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("dateTime").value("2040-01-01T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(5.2))
    }

}