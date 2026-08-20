package com.example.thymeleafexample.entity;

import com.example.thymeleafexample.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@AllArgsConstructor @Getter @Setter @Builder @NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date must be today or in the future")
    @Column(nullable = false)
    private Date date;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private Time startTime;

    @NotNull(message = "End time is required")
    @Column(nullable = false)
    private Time endTime;

    @NotNull(message = "Doctor is required")
    @ManyToOne
    private Doctor doctor;

    @NotNull(message = "Patient is required")
    @ManyToOne
    private Patient patient;

    @NotNull(message = "Appointment status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
}
