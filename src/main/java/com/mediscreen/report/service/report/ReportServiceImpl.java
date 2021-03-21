package com.mediscreen.report.service.report;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.service.note.NoteService;
import com.mediscreen.report.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private NoteService noteService;
    private PatientService patientService;

    @Autowired
    public ReportServiceImpl(NoteService noteService, PatientService patientService) {
        this.noteService = noteService;
        this.patientService = patientService;
    }

    @Override
    public Report getReport(int patientId) {
        Patient patient = patientService.findPatientInList(patientId);
        int age = getAge(patient.getDob());
        char sex = patient.getSex();
        List<Note> notesList = noteService.getPatientNotes(patientId);
        int occurrences = getNumberOfTriggerTermOccurrences(notesList);
        String riskLevel;

        if (occurrences == 0) {
            riskLevel = "None";
        } else if (occurrences == 2 && age >= 30) {
            riskLevel = "Borderline";
        } else if (occurrences == 3 && age < 30 && sex == 'M') {
            riskLevel = "In Danger";
        } else if (occurrences == 4 && age < 30 && sex == 'F') {
            riskLevel = "In Danger";
        } else if (occurrences == 6 && age >= 30) {
            riskLevel = "In Danger";
        } else if (occurrences == 5 && age < 30 && sex == 'M') {
            riskLevel = "Early Onset";
        } else if (occurrences == 7 && age < 30 && sex == 'F') {
            riskLevel = "Early Onset";
        } else if (occurrences >= 8 && age >= 30) {
            riskLevel = "Early Onset";
        } else {
            riskLevel = "Error: Not enough data";
        }

        Report report = new Report();
        report.setPatientId(patientId);
        report.setAge(age);
        report.setRiskLevel(riskLevel);
        return report;
    }

    @Override
    public int getNumberOfTriggerTermOccurrences(List<Note> noteList) {
        String patientNotes = "";
        if (noteList != null) {
            for (Note note : noteList) {
                patientNotes += note.getNote();
            }
            Map<String, Integer> occurrencesMap = new HashMap<>();
            List<String> triggerWords = Arrays.asList(patientNotes.replaceAll(",", " ").split(" "));
            for (String word : triggerWords) {
                Integer occurrence = occurrencesMap.get(word);
                occurrencesMap.put(word.toLowerCase(), (occurrence == null) ? 1 : occurrence +1);
            }
            int occurrences = 0;
            return occurrences;
        }
        return 0;
    }


    @Override
    public int getAge(LocalDate dob) {
//        LocalDate localDate = LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE);
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
