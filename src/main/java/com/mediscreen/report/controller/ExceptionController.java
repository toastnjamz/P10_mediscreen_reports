package com.mediscreen.report.controller;

import com.mediscreen.report.exception.MicroserviceNotFoundException;
import com.mediscreen.report.exception.PatientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * Exception Handler for MicroserviceNotFoundException
     * @param request
     * @param exception
     * @param model
     * @return ModelAndView
     */
    @ExceptionHandler({MicroserviceNotFoundException.class})
    public ModelAndView microserviceErrorHandler(HttpServletRequest request, Exception exception, Model model) {
        ModelAndView mav = new ModelAndView();
        model.addAttribute("exception", exception.getMessage());
        model.addAttribute("url", request.getRequestURL());
        mav.setViewName("error/genericerror");
        log.error(request.getRequestURI() + "threw" + exception);
        return mav;
    }

    /**
     * Exception Handler for PatientNotFoundException
     * @param request
     * @param exception
     * @param model
     * @return ModelAndView
     */
    @ExceptionHandler({PatientNotFoundException.class})
    public ModelAndView patientErrorHandler(HttpServletRequest request, Exception exception, Model model) {
        ModelAndView mav = new ModelAndView();
        model.addAttribute("exception", exception.getMessage());
        model.addAttribute("url", request.getRequestURL());
        mav.setViewName("error/genericerror");
        log.error(request.getRequestURI() + "threw" + exception);
        return mav;
    }

}
