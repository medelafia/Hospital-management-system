package com.example.thymeleafexample.repository;

import com.example.thymeleafexample.entity.Doctor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor , Integer > {
    public List<Doctor> findAllByFirstNameContainingIgnoreCase(String search) ;

    List<Doctor> findAllByPublishedTrue();
}
