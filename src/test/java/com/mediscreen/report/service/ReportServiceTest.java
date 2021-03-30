package com.mediscreen.report.service;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.service.note.NoteServiceClient;
import com.mediscreen.report.service.patient.PatientServiceClient;
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
    private NoteServiceClient noteServiceClientMock;

    @Mock
    private PatientServiceClient patientServiceClientMock;

    @InjectMocks
    ReportServiceImpl reportService;

    private Patient patient;
    private Note note;

    @BeforeAll
    public void setup() {
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        patient = new Patient();
        patient.setId(100);
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');

        note = new Note();
        note.setId("123");
        note.setNote("Test note");
        note.setPatientId(100);
    }

    @Test
    public void getReport_patientsExist_reportReturned() {
        // arrange
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);

        Report report = new Report();
        report.setPatientId(100);
        report.setAge(22);
        report.setRiskLevel("None");

        when(patientServiceClientMock.findPatientInList(100)).thenReturn(patient);

        // act
        Report result = reportService.getReport(100);

        // assert
        assertEquals(result.getRiskLevel(), "None");
    }

    @Test
    public void getNumberOfTriggerTermOccurrences_noTermsInList_zeroReturned() {
        // arrange
        Note note1 = new Note();
        note1.setId("123");
        note1.setNote("Test note");
        note1.setPatientId(100);

        List<Note> noteList = new ArrayList<>();
        noteList.add(note1);

        // act
        int result = reportService.getNumberOfTriggerTermOccurrences(noteList);

        // assert
        assertEquals(result, 0);
    }

    @Test
    public void getNumberOfTriggerTermOccurrences_twoTermsInList_twoReturned() {
        // arrange
        Note note1 = new Note();
        note1.setId("123");
        note1.setNote("Abnormal Smoker ");
        note1.setPatientId(100);

        List<Note> noteList = new ArrayList<>();
        noteList.add(note1);

        // act
        int result = reportService.getNumberOfTriggerTermOccurrences(noteList);

        // assert
        assertEquals(result, 2);
    }

    @Test
    public void getAge_validInput_ageReturned() {
        // arrange
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));

        // act
        int result = reportService.getAge(dob);

        // assert
        assertEquals(result, 22);
    }

}
