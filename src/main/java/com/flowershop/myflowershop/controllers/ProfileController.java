package com.flowershop.myflowershop.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.services.UserSecurityService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserSecurityService userSecurityService;

    @GetMapping
    String getProfile(Model model, Principal principal){
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }
}
