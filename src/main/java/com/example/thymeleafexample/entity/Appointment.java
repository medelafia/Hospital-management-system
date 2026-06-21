package com.example.thymeleafexample.entity;

import com.example.thymeleafexample.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@AllArgsConstructor @Getter @Setter @Builder @NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private Date date ;
    private Time startTime ;
    private Time endTime ;
    @ManyToOne
    private Doctor doctor ;
    @ManyToOne
    private Patient patient ;
    private AppointmentStatus status ;
}
