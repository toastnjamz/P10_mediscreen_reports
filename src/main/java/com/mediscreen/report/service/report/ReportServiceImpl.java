package com.mediscreen.report.service.report;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.service.note.NoteServiceClient;
import com.mediscreen.report.service.patient.PatientServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportServiceImpl implements ReportService {

    private NoteServiceClient noteServiceClient;
    private PatientServiceClient patientServiceClient;

    private List<String> triggerTermsList = Arrays.asList(
            "hemoglobina1c",
            "microalbumin",
            "bodyheight",
            "bodyweight",
            "smoker",
            "abnormal",
            "cholesterol",
            "dizziness",
            "relapse",
            "reaction",
            "antibodies");

    @Autowired
    public ReportServiceImpl(NoteServiceClient noteServiceClient, PatientServiceClient patientServiceClient) {
        this.noteServiceClient = noteServiceClient;
        this.patientServiceClient = patientServiceClient;
    }

    /**
     * Generates a diabetes assessment for a patient based on their age, gender, and the number of times
     * trigger terms appear in their note history
     * @param patientId
     * @return
     */
    @Override
    public Report getReport(int patientId) {
        Patient patient = patientServiceClient.findPatientInList(patientId);
        int age = getAge(patient.getDob());
        char sex = patient.getSex();
        List<Note> notesList = noteServiceClient.getPatientNotes(patientId);
        int occurrences = getNumberOfTriggerTermOccurrences(notesList);
        String riskLevel;

        if (occurrences < 2) {
            riskLevel = "None";
        } else if (occurrences == 2 && age > 30) {
            riskLevel = "Borderline";
        } else if (occurrences == 3 && age < 30 && sex == 'M') {
            riskLevel = "In Danger";
        } else if (occurrences == 3 && age >= 30) {
            riskLevel = "Borderline";
        } else if (occurrences == 4 && age < 30 && sex == 'F') {
            riskLevel = "In Danger";
        } else if (occurrences == 4 && age >= 30) {
            riskLevel = "Borderline";
        } else if (occurrences == 6 && age >= 30) {
            riskLevel = "In Danger";
        } else if (occurrences == 6 && age < 30) {
            riskLevel = "Borderline";
        } else if (occurrences == 5 && age < 30 && sex == 'M') {
            riskLevel = "Early Onset";
        } else if (occurrences == 5 && age >= 30) {
            riskLevel = "In Danger";
        } else if (occurrences == 7 && age < 30 && sex == 'F') {
            riskLevel = "Early Onset";
        } else if (occurrences == 7 && age >= 30) {
            riskLevel = "In Danger";
        } else if (occurrences >= 8 && age >= 30) {
            riskLevel = "Early Onset";
        } else {
            riskLevel = "Something went wrong";
        }

        Report report = new Report();
        report.setPatientId(patientId);
        report.setAge(age);
        report.setRiskLevel(riskLevel);
        return report;
    }

    /**
     * Calculates the number of times trigger terms appear in a patient's note history
     * @param noteList
     * @return int
     */
    @Override
    public int getNumberOfTriggerTermOccurrences(List<Note> noteList) {
        AtomicInteger occurrences = new AtomicInteger();
        String allNotesForAPatient = "";
        if (noteList != null) {
            for (Note note : noteList) {
                allNotesForAPatient += note.getNote();
            }

            String formattedNotes = allNotesForAPatient.toLowerCase().replaceAll(" ", "")
                    .replaceAll("\\n", "");

            triggerTermsList.forEach(term -> {
              if (formattedNotes.contains(term)) {
                occurrences.incrementAndGet();
                }
            });
            return occurrences.intValue();
        }
        return 0;
    }

    /**
     * Calculates a patient's age based on their birthdate
     * @param dob
     * @return int
     */
    @Override
    public int getAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
