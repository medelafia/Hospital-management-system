package com.example.thymeleafexample.entity;

import com.example.thymeleafexample.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
import java.util.List;


@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Patient  {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id ;
     private String firstName ;
     private String lastName ;
     private String cin ;
     private Date birthDate ;
     private Gender gender ;


     @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL , orphanRemoval = true)
     @JsonIgnore
     private List<Appointment> appointments ;

     @OneToOne(mappedBy = "patient")
     @JsonIgnore
     public User user;

}
