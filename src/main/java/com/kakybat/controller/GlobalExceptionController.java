package com.kakybat.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){

        String errorMessage = null;
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        if(exception.getMessage() != null){
            errorMessage = exception.getMessage();
        } else if(exception.getCause() != null){
            errorMessage = exception.getCause().getMessage();
        } else if(exception != null){
            errorMessage = exception.toString();
        }
        errorPage.addObject("errorMessage", errorMessage);
        return errorPage;
    }
}
