package com.mediscreen.report.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends Throwable {

    private static final Logger log = LoggerFactory.getLogger(PatientNotFoundException.class);

    public PatientNotFoundException() {
        super("The patient ID was not found.");
        log.error("The patient ID was not found.");
    }

}
