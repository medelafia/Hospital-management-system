package com.example.thymeleafexample;

import com.example.thymeleafexample.customExceptions.UsernameAlreadyTakenException;
import com.example.thymeleafexample.entity.Role;
import com.example.thymeleafexample.entity.User;
import com.example.thymeleafexample.repository.RoleRepository;
import com.example.thymeleafexample.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;


@SpringBootApplication
public class ThymeleafExampleApplication {
    @Value("${app.root.username}")
    private String rootUsername;
    @Value("${app.root.password}")
    private String rootPassword;

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafExampleApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() ;
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService, RoleRepository repository) {
        return args -> {
            System.out.println(this.rootUsername + " " + this.rootPassword);
            try {
                Role role =  Role.builder().roleName("ROLE_ADMIN").build();
                if(!repository.findByRoleName(role.getRoleName()).isPresent()) {
                    role = repository.save(role) ;
                }

                User user = User.builder()
                        .username(rootUsername)
                        .roles(Set.of(role))
                        .password(this.rootPassword)
                        .build();
                userService.save(user);
            }catch (UsernameAlreadyTakenException e){
                System.out.println(e.getMessage());
            }
        } ;
    }

}
