package com.example.thymeleafexample.service;

import com.example.thymeleafexample.customExceptions.EntityNotFoundException;
import com.example.thymeleafexample.entity.Patient;
import com.example.thymeleafexample.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepo patientRepo ;



    public Long getPatientsCount() {
        return patientRepo.count();
    }
    public Patient addPatient(Patient patient) {
        return patientRepo.save(patient ) ;
    }
    public List<Patient> getAllPatients() {
        return patientRepo.findAll() ;
    }
    public List<Patient> getAllPatients(String search) {
        return patientRepo.findAllByCinContainingIgnoreCase(search) ;
    }
    public void deletePatientById(int id ) {
        this.patientRepo.deleteById(id);
    }
    public Patient getPatientById(int id) {
        return this.patientRepo.findById(id).orElseThrow() ;
    }
    public Patient updatePatient(Patient patient) {
        Patient patientToUpdate = patientRepo.findById(patient.getId()).orElseThrow(() -> new EntityNotFoundException("Patient not found")) ;
        patientToUpdate.setFirstName(patient.getFirstName());
        patientToUpdate.setLastName(patient.getLastName());
        patientToUpdate.setGender(patient.getGender());
        patientToUpdate.setBirthDate(patient.getBirthDate());
        patientToUpdate.setCin(patient.getCin());

        return this.patientRepo.save(patientToUpdate) ;
    }
}
