package com.example.thymeleafexample.repository;

import com.example.thymeleafexample.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepo extends JpaRepository<Patient, Integer > {
    public List<Patient> findAllByCinContainingIgnoreCase(String search) ;
}

