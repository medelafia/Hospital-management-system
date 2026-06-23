package com.example.thymeleafexample.controller;

import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.service.AppointmentService;
import com.example.thymeleafexample.service.DoctorService;
import com.example.thymeleafexample.service.PatientService;
import com.example.thymeleafexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class GlobalController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;


    @GetMapping
    public String home() {
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String index(Model model) {
        List<Appointment> appointments = appointmentService.getTodayAppointments() ;
        model.addAttribute("usersCount" , userService.getUserCount());
        model.addAttribute("patientsCount" , patientService.getPatientsCount());
        model.addAttribute("appointmentsCount" , appointmentService.getAppointmentsCount());
        model.addAttribute("doctorsCount" , doctorService.getDoctorsCount());
        model.addAttribute("appointments" , appointments != null ? appointments : Collections.emptyList());

        return "index" ;
    }

    @GetMapping("/login")
    public String login(Model model ,@RequestParam(required = false) String  error ) {
        if(error != null) model.addAttribute("error", error);
        return "login" ;
    }
}
