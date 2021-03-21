package com.mediscreen.report.controller;

import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.service.patient.PatientService;
import com.mediscreen.report.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    PatientService patientService;

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    @GetMapping("/report/assessment/{patientId}")
    public ModelAndView showReportByPatientId(@PathVariable("patientId") Integer patientId, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientInList(patientId);
        if (patient != null) {
            Report report = reportService.getReport(patientId);
            model.addAttribute("patient", patient);
            model.addAttribute("age", report.getAge());
            model.addAttribute("riskLevel", report.getRiskLevel());
            mav.setViewName("report/assessment");
        }
        log.info("GET request received for showReportByPatientId()");
        return mav;

    }


}
