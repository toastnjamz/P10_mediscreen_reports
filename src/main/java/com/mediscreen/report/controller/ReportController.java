package com.mediscreen.report.controller;

import com.mediscreen.report.domain.patient.Patient;
import com.mediscreen.report.domain.report.Report;
import com.mediscreen.report.exception.MicroserviceNotFoundException;
import com.mediscreen.report.service.patient.PatientServiceClient;
import com.mediscreen.report.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutionException;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    PatientServiceClient patientServiceClient;

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    /**
     * HTTP GET request loads a list of patients
     * @param model
     * @return ModelAndView
     * @throws MicroserviceNotFoundException
     */
    @GetMapping(value = "/patient/list")
    public ModelAndView getAllPatients(Model model) {
        ModelAndView mav = new ModelAndView();
        try {
            mav.addObject("patientList", patientServiceClient.getPatientsList());
            mav.setViewName("patient/list");
            log.info("GET request received for getAllPatients()");
        } catch (Exception e) {
            throw new MicroserviceNotFoundException();
        }
        return mav;

    }

    /**
     * HTTP GET request generates a diabetes assessment based on the patient's id
     * @param patientId
     * @param model
     * @return ModelAndView
     * @throws MicroserviceNotFoundException
     */
    @GetMapping("/report/assessment/{patientId}")
    public ModelAndView showReportByPatientId(@PathVariable("patientId") Integer patientId, Model model)
            throws MicroserviceNotFoundException {
        ModelAndView mav = new ModelAndView();
        try {
            Patient patient = patientServiceClient.findPatientInList(patientId);
            if (patient != null) {
                Report report = reportService.getReport(patientId);
                model.addAttribute("patient", patient);
                model.addAttribute("report", report);
                mav.setViewName("report/assessment");
            }
            log.info("GET request received for showReportByPatientId()");
        } catch (Exception e) {
            throw new MicroserviceNotFoundException();
        }
        return mav;
    }

}
