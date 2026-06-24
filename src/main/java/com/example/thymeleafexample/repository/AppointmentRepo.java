package com.example.thymeleafexample.repository;

import com.example.thymeleafexample.entity.Appointment;
import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.entity.Patient;
import org.aspectj.weaver.patterns.AndPointcut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("""
    SELECT MONTH(a.date) , COUNT(a)
    FROM Appointment a
    WHERE YEAR(a.date) = :year
    GROUP BY MONTH(a.date)
    ORDER BY MONTH(a.date)
    """)
    List<Object[]> countAppointmentsByMonth(@Param("year") int year);

}
