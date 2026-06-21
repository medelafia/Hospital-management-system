package com.example.thymeleafexample.controller;

import com.example.thymeleafexample.customExceptions.UsernameAlreadyTakenException;
import com.example.thymeleafexample.entity.Role;
import com.example.thymeleafexample.entity.User;
import com.example.thymeleafexample.repository.RoleRepository;
import com.example.thymeleafexample.service.AccountService;
import com.example.thymeleafexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String usersPage(Model model , @RequestParam(defaultValue = "5")int size , @RequestParam(defaultValue = "0")int pageNumber , @RequestParam(required = false) String errorMessage ,@RequestParam(required = false) String successMessage) {
        model.addAttribute("users" , this.userService.findAll(PageRequest.of(pageNumber, size))) ;
        model.addAttribute("size", size) ;
        model.addAttribute("pageNumber", pageNumber) ;
        model.addAttribute("errorMessage", errorMessage) ;
        model.addAttribute("successMessage", successMessage) ;

        return "users";
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addUser( @RequestParam String username,
                      @RequestParam String email,
                      @RequestParam String password,
                      @RequestParam String roles
    ) {
        Role role = roleRepository.findById(roles)
                .orElseGet(() -> roleRepository.save(
                        Role.builder().roleName(roles).build()
                ));
        try {
            this.userService.save(User.builder().username(username).email(email).password(password).roles(Set.of(role)).build()) ;
            return "redirect:/user/?successMessage=User added successfully" ;
        }catch (UsernameAlreadyTakenException e){
            return "redirect:/user/?errorMessage=Username already taken" ;
        }
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable int id, Model model) {
        this.userService.delete(id) ;
        return "redirect:/user/?successMessage=User deleted successfully" ;
    }
    @GetMapping("/change-password")
    public String changePassword(Model model , @RequestParam(required = false) String errorMessage ,@RequestParam(required = false) String successMessage ) {
        model.addAttribute("errorMessage", errorMessage) ;
        model.addAttribute("successMessage", successMessage) ;

        return "changePassword" ;
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword, @RequestParam String newPassword , @RequestParam String confirmPassword , @RequestParam(required = false  , defaultValue = "true") boolean logout) {
        if(!newPassword.equals(confirmPassword))
            return "redirect:/user/change-password?errorMessage=Passwords do not match" ;
        if(!userService.checkPassword(currentPassword))
            return "redirect:/user/change-password?errorMessage=the password incorrect" ;

        this.userService.updatePassword(newPassword);
        if(!logout)
            return "redirect:/user/change-password?successMessage=Password changed successfully";
        else
            return "redirect:/logout";
    }
}
