package com.mediscreen.report.service.report;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.report.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    public Report getReport(int patientId);

    public int getNumberOfTriggerTermOccurrences(List<Note> noteList);

    public int getAge(LocalDate dob);
}
