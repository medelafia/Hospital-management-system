package com.example.thymeleafexample.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Role {
    @Id
    private String roleName ;
}
