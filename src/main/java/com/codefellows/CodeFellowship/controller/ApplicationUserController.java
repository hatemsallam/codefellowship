package com.codefellows.CodeFellowship.controller;

import com.codefellows.CodeFellowship.domain.ApplicationUser;
import com.codefellows.CodeFellowship.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder encoder;


    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }
    @PostMapping("/signup")
    public RedirectView signup(Model model,@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String dateOfBirth,
                               @RequestParam String bio){

        ApplicationUser applicationUser = new ApplicationUser(username, encoder.encode(password),firstName,lastName,dateOfBirth,bio);
        applicationUser= applicationUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addAttribute("user",applicationUser);
        return new RedirectView("/profile");
    }


    @GetMapping("/login")
    public String signinPage(){
        return"signin";

    }
    @GetMapping("/users/{userName}")
    public String getProfilePage(@PathVariable String userName, Model model) {
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserByUsername(userName).orElse(null);
        model.addAttribute("user",applicationUser);

        return "profile";
    }
    @GetMapping("/profile")
    public String gitProfile(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername()).orElse(null);
        if(applicationUser == null){
            return "error";
        }
        model.addAttribute("user",applicationUser);
        return "profile";
    }
}