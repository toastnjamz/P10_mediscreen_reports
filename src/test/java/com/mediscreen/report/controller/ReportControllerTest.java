package com.mediscreen.report.controller;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
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

import static org.mockito.Mockito.when;
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
    private Note note;
    private Report report;

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

        note = new Note();
        note.setId("123");
        note.setNote("Test note");
        note.setPatientId(100);

        report = new Report();
        report.setAge(50);
        report.setRiskLevel("In Danger");
        report.setPatientId(100);
    }

    @Test
    public void getAllPatients_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/patient/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("patient/list"));
    }

    @Test
    public void showReportByPatientId_statusIsSuccessful() throws Exception {
        when(patientServiceClient.findPatientInList(100)).thenReturn(patient);
        when(reportService.getReport(100)).thenReturn(report);

        mockMvc.perform(get("/report/assessment/{patientId}", "100"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("report/assessment"));
    }

}
