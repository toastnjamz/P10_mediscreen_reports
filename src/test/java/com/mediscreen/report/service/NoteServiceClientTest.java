package com.mediscreen.report.service;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.service.note.NoteServiceClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NoteServiceClientTest {

    private NoteServiceClient noteServiceClient;
    private RestTemplate restTemplate;
    private Patient patient;
    private Note note;

    @BeforeAll
    public void setup() {
        restTemplate = new RestTemplate();
        noteServiceClient = new NoteServiceClient(restTemplate);

        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        patient = new Patient();
        patient.setId(100);
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
    }

    @Test
    public void getPatientNotes_patientExists_notesListReturned() {
        // arrange

        // act
        List<Note> listResult = noteServiceClient.getPatientNotes(1);

        // assert
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void getPatientNotes_patientDoesNotExist_nullReturned() {
        // arrange

        // act
        List<Note> listResult = noteServiceClient.getPatientNotes(200);

        // assert
        assertEquals(listResult.size(), 0);
    }

}
