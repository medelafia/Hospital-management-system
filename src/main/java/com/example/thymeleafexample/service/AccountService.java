package com.example.thymeleafexample.service;


import com.example.thymeleafexample.entity.Doctor;
import com.example.thymeleafexample.entity.Role;
import com.example.thymeleafexample.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AccountService {


    public User getPrincipal() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return (User) securityContext.getAuthentication().getPrincipal() ;
    }

    public boolean isAdmin() {
        return this.getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public boolean isPatient() {
        return this.getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }
    public boolean isDoctor() {
        return this.getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCTOR"));
    }

}
