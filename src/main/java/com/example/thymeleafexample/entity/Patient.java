package com.example.thymeleafexample.entity;

import com.example.thymeleafexample.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Date;
import java.util.List;


@Entity @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Patient  {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id ;

     @NotBlank(message = "First name is required")
     @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
     @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "First name can only contain letters, spaces, apostrophes and hyphens")
     @Column(nullable = false)
     private String firstName;

     @NotBlank(message = "Last name is required")
     @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
     @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Last name can only contain letters, spaces, apostrophes and hyphens")
     @Column(nullable = false)
     private String lastName;

     @NotBlank(message = "CIN is required")
     @Size(min = 6, max = 20, message = "CIN must be between 6 and 20 characters")
     @Pattern(regexp = "^[A-Z0-9]+$", message = "CIN can only contain uppercase letters and numbers")
     @Column(unique = true, nullable = false)
     private String cin;

     @NotNull(message = "Birth date is required")
     @Past(message = "Birth date must be in the past")
     @Column(nullable = false)
     private Date birthDate;

     @NotNull(message = "Gender is required")
     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private Gender gender;

     @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
     @JsonIgnore
     private List<Appointment> appointments;

     @OneToOne(mappedBy = "patient")
     @JsonIgnore
     private User user;
}
