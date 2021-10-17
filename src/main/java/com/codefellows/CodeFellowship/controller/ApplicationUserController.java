package com.codefellows.CodeFellowship.controller;

import com.codefellows.CodeFellowship.domain.ApplicationUser;
import com.codefellows.CodeFellowship.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping(value = "/signup")
    public String getSignUp() {
        return "signup";
    }


    @PostMapping(value = "/signup")
    public RedirectView attemptSignUp(@RequestParam String username ,
                                      @RequestParam String password ,
                                      @RequestParam String firstname ,
                                      @RequestParam String lastname ,
                                      @RequestParam Date dateofbirth ,
                                      @RequestParam String bio) {
        ApplicationUser newUser = new ApplicationUser(username , encoder.encode(password), firstname , lastname , dateofbirth , bio);
        applicationUserRepository.save(newUser);
        return new RedirectView("/");
    }




}
