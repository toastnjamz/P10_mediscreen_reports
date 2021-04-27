package com.mediscreen.report.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MicroserviceNotFoundException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(MicroserviceNotFoundException.class);

    public MicroserviceNotFoundException() {
        super("One or more microservices are unavailable.");
        log.error("One or more microservices are unavailable.");
    }
}
