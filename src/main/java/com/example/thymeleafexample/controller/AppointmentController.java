package com.example.thymeleafexample.controller;


import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.enums.AppointmentStatus;
import com.example.thymeleafexample.service.AccountService;
import com.example.thymeleafexample.service.AppointmentService;
import com.example.thymeleafexample.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    public AppointmentService appointmentService;
    @Autowired
    public DoctorService doctorService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String appointmentsPage(Model model ,
                                   @RequestParam(required = false) String status ,
                                   @RequestParam(required = false , defaultValue = "-1") int doctorId ,
                                   @RequestParam(required = false) String date ,
                                   @RequestParam(required = false , defaultValue = "5") int size ,
                                   @RequestParam(required = false , defaultValue = "1") int pageNumber
                                   ) {

        Page<Appointment> page = this.appointmentService.getAllAppointments(PageRequest.of(pageNumber - 1, size)) ;
        List<Appointment> appointments = page.stream().toList() ;

        // filter by date
        if(date != null && !date.isEmpty() ) {
            appointments = appointments.stream()
                    .filter(appointment -> appointment.getDate().compareTo(Date.valueOf(date)) == 0)
                    .collect(Collectors.toList());
        }

        // filter by status
        if(status != null  && !status.equals("NONE")) {
            appointments = appointments.stream().filter(appointment -> appointment.getStatus().equals(AppointmentStatus.valueOf(status))).collect(Collectors.toList());

        }

        // filter by doctor id
        if(doctorId > 0) {
            appointments = appointments.stream().filter(appointment -> appointment.getDoctor().getId() == doctorId).collect(Collectors.toList());
        }

        model.addAttribute("date", date);
        model.addAttribute("status", status);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("appointments",  appointments) ;
        model.addAttribute("size", size);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("numberOfPages", IntStream.range(1 , page.getTotalPages() + 1).toArray());
        model.addAttribute("doctors", this.doctorService.getAllDoctors() ) ;
        return "appointments" ;
    }
    @PostMapping("/edit-status")
    public String editAppointmentStatus(@RequestParam("id") int id ,@RequestParam("status") String status ,@RequestParam("doctorId") int doctorId , HttpServletRequest request) {
        this.appointmentService.editAppointmentStatus(id, status);
        return "redirect:/doctor/"+doctorId+"?successMessage=Appointment Edited";
    }
}
