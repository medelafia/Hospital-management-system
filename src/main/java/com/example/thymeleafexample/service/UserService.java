package com.example.thymeleafexample.service;


import com.example.thymeleafexample.customExceptions.EntityNotFoundException;
import com.example.thymeleafexample.customExceptions.UsernameAlreadyTakenException;
import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.entity.Patient;
import com.example.thymeleafexample.entity.Role;
import com.example.thymeleafexample.entity.User;
import com.example.thymeleafexample.repository.DoctorRepo;
import com.example.thymeleafexample.repository.PatientRepo;
import com.example.thymeleafexample.repository.RoleRepository;
import com.example.thymeleafexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PatientRepo patientRepo ;
    @Autowired
    private DoctorRepo doctorRepo ;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleRepository roleRepository;



    public Long getUserCount() {
        return this.userRepository.count();
    }
    public User save(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException(user.getUsername() + " already exists");
        }

        user.getRoles().forEach(role -> {

            System.out.println(role.getRoleName());
        }) ;
        Role role = user.getRoles().stream().findFirst().get() ;
        if(role != null && !this.roleRepository.findByRoleName(role.getRoleName()).isPresent()) {
            this.roleRepository.save(role);
        }

        if(role.getRoleName().equals("ROLE_PATIENT")) {
            Patient patient = this.patientRepo.save(Patient.builder().cin("Not Set").firstName("Not Set").lastName("Not Set").build());
            user.setPatient(patient);
        }else if(role.getRoleName().equals("ROLE_DOCTOR")) {
            Doctor doctor = this.doctorRepo.save(Doctor.builder().speciality("Not Set").firstName("Not Set").lastName("Not Set").build());
            user.setDoctor(doctor);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user) ;
    }


    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public void delete(int id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
        user.getRoles().clear();
        this.userRepository.save(user);
        this.userRepository.delete(user);
    }

    public boolean checkPassword(String password) {
        User user = this.userRepository.findByUsername(accountService.getPrincipal().getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return passwordEncoder.matches(password, user.getPassword());
    }

    public void updatePassword( String newPassword) {
        User user = this.userRepository.findByUsername(accountService.getPrincipal().getUsername()).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
    }
}
