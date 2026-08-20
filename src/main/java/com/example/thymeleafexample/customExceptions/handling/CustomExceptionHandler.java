package com.example.thymeleafexample.customExceptions.handling;


import com.example.thymeleafexample.customExceptions.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {



    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException e , Model model) {
        model.addAttribute("message", e.getMessage());
        return "notFound" ;
    }



}
