package com.deepak.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException e) {
        ModelAndView modelAndView = new ModelAndView("usernameExistError");
        modelAndView.addObject("errorMessage", "Username already exists");
        return modelAndView;
    }
}
