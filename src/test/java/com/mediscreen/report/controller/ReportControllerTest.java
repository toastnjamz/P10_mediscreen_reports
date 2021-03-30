package com.mediscreen.report.controller;

import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.service.patient.PatientServiceClient;
import com.mediscreen.report.service.report.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ReportControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    ReportService reportService;

    @MockBean
    PatientServiceClient patientServiceClient;

    private Patient patient;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();

        patient = new Patient();
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        patient = new Patient();
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');
        patient.setAddress("123 Test Street");
        patient.setPhone("555-555-5555");
    }

    @Test
    public void getAllPatients_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/patient/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("patient/list"));
    }

    @Test
    public void showReportByPatientId_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/report/assessment/" + 100))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("report/assessment"));
    }


}
