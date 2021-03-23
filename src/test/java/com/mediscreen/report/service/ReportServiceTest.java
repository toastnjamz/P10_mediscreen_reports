package com.mediscreen.report.service;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.service.note.NoteService;
import com.mediscreen.report.service.patient.PatientService;
import com.mediscreen.report.service.report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportServiceTest {

    @Mock
    private NoteService noteServiceMock;

    @Mock
    private PatientService patientServiceMock;

    @InjectMocks
    ReportServiceImpl reportService;

    private Patient patient;
    private Note note;

    @BeforeAll
    public void setup() {
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient = new Patient();
        patient.setId(100);
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');

        Note note = new Note();
        note.setId("123");
        note.setNote("Test note");
        note.setPatientId(100);
    }

    @Test
    public void getReport_patientsExist_patientsReturned() {
        // arrange
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);

        when(patientServiceMock.findPatientInList(100)).thenReturn(patient);

        // act
        Report report = reportService.getReport(patient.getId());

        // assert
        assertEquals(report.getPatientId(), patient.getId());
    }

}
