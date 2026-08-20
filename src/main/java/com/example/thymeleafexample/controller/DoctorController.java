package com.example.thymeleafexample.controller;

import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.entity.User;
import com.example.thymeleafexample.service.AccountService;
import com.example.thymeleafexample.service.AppointmentService;
import com.example.thymeleafexample.service.DoctorService;
import com.example.thymeleafexample.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService ;
    @Autowired
    private AppointmentService appointmentService ;
    @Autowired
    private PatientService patientService ;
    @Autowired
    private AccountService accountService ;


    @GetMapping("/")
    public String doctorIndex(Model model , @RequestParam(required = false , defaultValue = "")String successMessage , @RequestParam(required = false , defaultValue = "")String search) {
        if(!successMessage.equalsIgnoreCase("")) model.addAttribute("successMessage" , successMessage ) ;
        model.addAttribute("search" ,search ) ;
        model.addAttribute("doctors" , search.isBlank() ? doctorService.getAllDoctors() : doctorService.getAllDoctors(search)) ;
        model.addAttribute("isAdmin" , accountService.isAdmin()) ;
        model.addAttribute("isPatient" , accountService.isPatient()) ;
        model.addAttribute("isDoctor" , accountService.isDoctor()) ;
        return "doctors" ;
    }

    @PostMapping("/")
    public String addDoctor(@RequestPart("image") MultipartFile image, @ModelAttribute @Valid Doctor doctor, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/doctor/" + doctor.getId();
        }

        doctorService.addDoctor(doctor , image) ;

        return "redirect:/doctor/?successMessage=added%20successfully" ;
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        doctorService.deleteDoctorById(id);
        return "redirect:/doctor/?successMessage=deleted%20successfully" ;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_DOCTOR')")
    public String doctorView(@PathVariable int id , Model model ,
                             @RequestParam(required=false) String successMessage
    ) {
        model.addAttribute("appointments" , appointmentService.getAllAppointmentsByDoctorId(id)) ;
        model.addAttribute("doctor" , doctorService.getDoctorById(id)) ;
        model.addAttribute("successMessage" , successMessage ) ;
        return "doctor" ;
    }
    @GetMapping("/my-profile")
    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
    public String myProfile(@RequestParam(required = false) String successMessage ) {
        int id = this.accountService.getPrincipal().getDoctor().getId();

        return "redirect:/doctor/" + id  ;
    }

    @PostMapping("/edit-doctor")
    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR' , 'ROLE_ADMIN')")
    public String editDoctor(@Valid Doctor doctor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/doctor/" + doctor.getId();
        }
        this.doctorService.editDoctor(doctor) ;

        return "redirect:/doctor/%d?successMessage=edited successfully".formatted(doctor.getId()) ;
    }


    @GetMapping("/check-available")
    public String checkAvailable(@RequestParam int doctorId , @RequestParam Date date , @RequestParam LocalTime time) {
        return "redirect:/doctor/"+doctorId+"?date=" + date + "&time=" + time + "&available="+!doctorService.checkAvailable(doctorId , date , Time.valueOf(time.getMinute() < 30 ? LocalTime.of(time.getHour() , time.getMinute()) : time) ) ;
    }

    @GetMapping("/image/{id}")
    public ResponseEntity getImage(@PathVariable int id) throws IOException {
        String path = "src/main/resources/static/images/"+id+".jpg" ;
        String defaultImagePath = "src/main/resources/static/images/default.jpg" ;
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        if(!fileSystemResource.exists()) {
            fileSystemResource = new FileSystemResource(defaultImagePath) ;
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileSystemResource) ;
    }
    @PostMapping("/{id}/editProfile")
    public String editProfile(@RequestParam("image") MultipartFile image ,@PathVariable int id) throws IOException {
        this.doctorService.editProfileImage(id , image);
        return "redirect:/doctor/" + id + "?successMessage=profile edited successfully" ;
    }

    @GetMapping("/{id}/book-appointment")
    public String bookAppointment(
            @PathVariable int id,
            @RequestParam( required = false ) Date selectedDate  ,
            @RequestParam( required = false ) Time selectedTime  ,
            @RequestParam( required = false , defaultValue = "1" ) int page ,
            Model model) {
        model.addAttribute("doctor" , doctorService.getDoctorById(id)) ;


        List<Date> days = new LinkedList<>() ;
        int start = (page - 1) * 7 +1 ;
        int end = page * 7 ;
        for( int i = start ; i <= end  ; i++ ) {
            LocalDate date = LocalDate.now().plusDays( i ) ;
            days.add( Date.valueOf(date) ) ;
        }

        List<Time> times = new LinkedList<>() ;
        List<Appointment> appointments = this.appointmentService.getAllAppointmentsByDoctorIdAndDate(id , selectedDate ) ;
        List<Time> reservedTimes =  appointments.stream().map(Appointment::getStartTime).toList() ;
        for(int i = 9 ; i <= 17 ; i++ ) {
            Time time = Time.valueOf(i + ":00:00") ;
            if(reservedTimes.stream().anyMatch(reservedTime -> reservedTime.compareTo(time) == 0 )) {
                continue;
            }
            times.add(time) ;
        }
        model.addAttribute("page" , page) ;
        model.addAttribute("days" , days) ;
        model.addAttribute("times" , times) ;
        model.addAttribute("selectedDate" ,selectedDate != null ? selectedDate : Date.valueOf(LocalDate.now().plusDays(1 )) ) ;
        model.addAttribute("selectedTime" , selectedTime  ) ;

        return "bookAppointment";
    }
    @PostMapping("/{doctorId}/book-appointment")
    public String bookAppointment(
            @PathVariable int doctorId ,
            @RequestParam Date selectedDate  ,
            @RequestParam  Time selectedTime
    ){

        int patientId = ((User)this.accountService.getPrincipal()).getPatient().getId() ;
        this.appointmentService.addAppointment(selectedDate , doctorId , patientId , selectedTime ) ;
        return "redirect:/patient/my-appointments";
    }
}

