package com.example.thymeleafexample.controller;

import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.entity.Patient;
import com.example.thymeleafexample.service.AccountService;
import com.example.thymeleafexample.service.AppointmentService;
import com.example.thymeleafexample.service.DoctorService;
import com.example.thymeleafexample.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService ;
    @Autowired
    private AccountService accountService ;
    @Autowired
    private AppointmentService appointmentService ;

    @GetMapping("/")
    public String patientIndex(Model model  ,
                               @RequestParam(required = false)String successMessage ,
                               @RequestParam(required = false , defaultValue = "") String search ) {
        if(successMessage != null ) model.addAttribute("successMessage" , successMessage ) ;
        model.addAttribute("search" , search) ;
        model.addAttribute("patients" ,search.isEmpty() ? patientService.getAllPatients() : patientService.getAllPatients(search))  ;
        return "patients" ;
    }

    @PostMapping("/")
    public String addPatient(Patient patient) {
        patientService.addPatient(patient) ;
        return "redirect:/patient/?successMessage=added%20successfully" ;
    }
    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        patientService.deletePatientById(id);
        return "redirect:/patient/?successMessage=deleted%20successfully" ;
    }
    @GetMapping("/my-profile")
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    public String patientProfileView(Model model, @RequestParam(required = false) String successMessage) {
        Patient patient = this.patientService.getPatientById(this.accountService.getPrincipal().getPatient().getId());

        model.addAttribute("patient" , patient) ;
        model.addAttribute("successMessage" , successMessage) ;
        return "patientProfile" ;
    }

    @GetMapping("/my-appointments")
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    public String myAppointmentsPage(Model model ,
                                     @RequestParam(required = false , defaultValue = "5") int size ,
                                     @RequestParam(required = false , defaultValue = "1") int pageNumber
    ) {
        int id =  this.accountService.getPrincipal().getPatient().getId() ;
        Page<Appointment> page = this.appointmentService.getAllAppointmentsByPatientId(id , PageRequest.of(pageNumber - 1, size)) ;
        List<Appointment> appointments = page.stream().toList() ;


        model.addAttribute("appointments",  appointments) ;
        model.addAttribute("size", size);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("numberOfPages", IntStream.range(1 , page.getTotalPages() + 1).toArray());
        return "patientAppointments" ;
    }

    @PostMapping("/edit-patient")
    public String editPatient(@ModelAttribute Patient patient) {
        this.patientService.updatePatient(patient);

        return "redirect:/patient/my-profile?successMessage=updated%20successfully" ;
    }
}
