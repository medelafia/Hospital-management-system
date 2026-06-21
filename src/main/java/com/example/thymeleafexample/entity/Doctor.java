package com.example.thymeleafexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.sql.Date;
import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Doctor  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String firstName ;
    private String lastName ;
    private String phone ;
    private String email ;
    private String speciality ;
    private int yearsOfExperience ;
    private Date birthDate ;
    private Date joinDate ;
    private boolean published ;

    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL  )
    @JsonIgnore
    private List<Appointment> appointments ;

    @OneToOne(mappedBy = "doctor")
    @JsonIgnore
    private User user ;
}