package com.example.thymeleafexample.service;

import com.example.thymeleafexample.customExceptions.EntityNotFoundException;
import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.repository.AppointmentRepo;
import com.example.thymeleafexample.repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    public DoctorRepo doctorRepo ;
    @Autowired
    public AppointmentRepo appointmentRepo ;

    public Long getDoctorsCount() {
        return this.doctorRepo.count();
    }
    public Doctor addDoctor(Doctor doctor , MultipartFile image) throws IOException {
        Doctor savedDoctor = doctorRepo.save(doctor) ;
        if(image != null && !image.isEmpty()) {
            String path = "src/main/resources/static/images/" + savedDoctor.getId() + ".jpg";
            image.transferTo(Path.of(path));
        }
        return savedDoctor ;
    }
    public void editProfileImage(int doctorId , MultipartFile image) throws IOException {
        String path = "src/main/resources/static/images/" + doctorId + ".jpg";
        Files.copy(image.getInputStream(), Path.of(path), StandardCopyOption.REPLACE_EXISTING);
    }
    public List<Doctor> getAllDoctors(String search) {
        return this.doctorRepo.findAllByFirstNameContainingIgnoreCase(search);
    }
    public void deleteDoctorById(int id) {
        this.doctorRepo.deleteById(id);
    }
    public Doctor editDoctor(Doctor doctor) {
        return this.doctorRepo.save(doctor);
    }
    public Doctor getDoctorById(int id) {
        return this.doctorRepo.findById(id).orElseThrow() ;
    }
    public List<Doctor> getAllDoctors() {
        return this.doctorRepo.findAllByPublishedTrue() ;
    }
    public boolean checkAvailable(int doctorId , Date date, Time time) {
        return appointmentRepo.findByDateAndStartTimeAndDoctor(date , time , doctorRepo.findById(doctorId).orElseThrow(() -> new EntityNotFoundException("Doctor not found")) ).isPresent() ;
    }

}
