package com.flowershop.myflowershop.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flowershop.myflowershop.models.Flower;
import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.services.FlowerService;
import com.flowershop.myflowershop.services.UserSecurityService;



@Controller
@RequestMapping
public class FlowerController {


    @Autowired
    private FlowerService flowerService;
    @Autowired
    private UserSecurityService userSecurityService;
    

    @GetMapping
    public String showFlowers(Model model){
        List<Flower> flowers = flowerService.getFlowers();
        model.addAttribute("flowers", flowers);
        return "flowers";
    }
    @GetMapping("/flowers/{id}")
    public String getFlowerById(@PathVariable("id") String id, Model model, Principal principal){
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());

        Long flowerId = Long.parseLong(id);
        Flower flower = flowerService.findById(flowerId).get(0);
        model.addAttribute("flower", flower);
        model.addAttribute("user", user);

        return "flower";
    }
}
