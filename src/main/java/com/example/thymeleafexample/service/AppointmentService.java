package com.example.thymeleafexample.service;

import com.example.thymeleafexample.customExceptions.EntityNotFoundException;
import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.entity.Patient;
import com.example.thymeleafexample.entity.Statistic;
import com.example.thymeleafexample.enums.AppointmentStatus;
import com.example.thymeleafexample.repository.AppointmentRepo;
import com.example.thymeleafexample.repository.DoctorRepo;
import com.example.thymeleafexample.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo ;
    @Autowired
    private DoctorRepo doctorRepo ;
    @Autowired
    private PatientRepo patientRepo ;

    Statistic statistic = new Statistic(null, null );

    public List<Appointment> getTodayAppointments(){
        return appointmentRepo.findAllByDate(Date.valueOf(LocalDate.now()));
    }
    public Long getAppointmentsCount() {
        return appointmentRepo.count();
    }
    public Appointment addAppointment(Date date, int doctorId , int patientId , Time time ) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(()-> new RuntimeException("Patient not found")) ;
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new RuntimeException("Doctor not found")) ;


        return appointmentRepo.save(Appointment.builder()
                        .startTime(time)
                        .endTime(Time.valueOf( time.toLocalTime().plus(1, ChronoUnit.HOURS)))
                        .doctor(doctor)
                        .patient(patient)
                        .date(date)
                        .status(AppointmentStatus.RESERVED)
                .build()
        ) ;
    }
    public List<Appointment> getAllAppointmentsByDoctorId(int id) {
        return appointmentRepo.findByDoctor(doctorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found")) ) ;
    }

    public Page<Appointment> getAllAppointmentsByPatientId(int id , Pageable pageable) {
        return appointmentRepo.findByPatient(patientRepo.findById(id).orElseThrow(), pageable) ;
    }
    public List<Appointment> getAllAppointmentsByDoctorIdAndDate(int doctorId, Date date) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new EntityNotFoundException("Doctor not found")) ;
        return appointmentRepo.findByDoctorAndDate(doctor, date) ;
    }

    public Page<Appointment> getAllAppointments(Pageable pageable) {
        return appointmentRepo.findAll(pageable) ;
    }
    public Appointment editAppointmentStatus(int id  , String status ) {
        Appointment appointment =  this.appointmentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Appointment not found")) ;
        appointment.setStatus(AppointmentStatus.valueOf(status));

        return appointmentRepo.save(appointment) ;
    }

    public Statistic getAppointmentStatistics(int year) {
        List<Long> values = new ArrayList<>();
        List<Object> labels = new ArrayList<>();
        for(Object[] obj : this.appointmentRepo.countAppointmentsByMonth(year)){
            values.add((Long) obj[1]);
            labels.add(Month.of((Integer) obj[0]).name());
        }
        this.statistic.setValues(values);
        this.statistic.setLabels(labels);
        return this.statistic ;
    }
}
