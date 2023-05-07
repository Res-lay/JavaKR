package com.flowershop.myflowershop.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.services.UserSecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private UserSecurityService userSecurityService;

    @GetMapping("/login")
	String login() {
		return "login";
	}

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }
    @PostMapping("/registration")
    public String signUpUser(@ModelAttribute("user") User user) {
        logger.info("Received request to sign up user with username: {}", user.getUsername());
        userSecurityService.signUpUser(user);
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

}
