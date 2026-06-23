package com.example.thymeleafexample.repository;

import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.entity.Patient;
import org.aspectj.weaver.patterns.AndPointcut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment , Integer> {
    public List<Appointment> findByDoctor(Doctor doctor) ;
    public Optional<Appointment> findByDateAndStartTimeAndDoctor(Date date , Time time , Doctor doctor ) ;

    List<Appointment> findByDoctorAndDate(Doctor doctor , Date date) ;

    Page<Appointment> findByPatient(Patient patient, Pageable pageable);
    List<Appointment> findAllByDate(Date date);
}
