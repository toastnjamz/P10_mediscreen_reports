package com.mediscreen.report.service.report;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.service.note.NoteService;
import com.mediscreen.report.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private NoteService noteService;
    private PatientService patientService;

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
    public ReportServiceImpl(NoteService noteService, PatientService patientService) {
        this.noteService = noteService;
        this.patientService = patientService;
    }

    /**
     * Generates a diabetes assessment for a patient based on their age, gender, and the number of times
     * trigger terms appear in their note history
     * @param patientId
     * @return
     */
    @Override
    public Report getReport(int patientId) {
        Patient patient = patientService.findPatientInList(patientId);
        int age = getAge(patient.getDob());
        char sex = patient.getSex();
        List<Note> notesList = noteService.getPatientNotes(patientId);
        int occurrences = getNumberOfTriggerTermOccurrences(notesList);
        String riskLevel;

        if (occurrences < 2 ) {
            riskLevel = "None";
        } else if (occurrences < 4 && age < 30) {
            riskLevel = "Borderline";
        } else if (occurrences >= 2 && age >= 30) {
            riskLevel = "Borderline";
        } else if (occurrences == 3 && age < 30 && sex == 'M') {
            riskLevel = "In Danger";
        } else if (occurrences == 4 && age < 30 && sex == 'F') {
            riskLevel = "In Danger";
        } else if (occurrences == 6 && age >= 30) {
            riskLevel = "In Danger";
        } else {
            riskLevel = "Early Onset";
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
        String allNotesForAPatient = "";
        if (noteList != null) {
            for (Note note : noteList) {
                allNotesForAPatient += note.getNote() + " ";
            }
            Map<String, Integer> wordCountMap = new HashMap<>();

            List<String> wordList = Arrays.asList(allNotesForAPatient.replaceAll(",", " ").replaceAll("\n", " ").split(" "));
            for (String word : wordList) {
                Integer numTimes = wordCountMap.get(word);
                wordCountMap.put(word.toLowerCase(), (numTimes == null) ? 1 : numTimes +1);
            }

            int occurrences = 0;
            for (String term : triggerTermsList) {
                if (wordCountMap.keySet().contains(term)) {
                    occurrences += wordCountMap.get(term.toLowerCase());
                }
            }
            return occurrences;
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
