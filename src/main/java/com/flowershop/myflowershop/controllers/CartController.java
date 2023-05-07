package com.flowershop.myflowershop.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.services.CartItemService;
import com.flowershop.myflowershop.services.CartService;
import com.flowershop.myflowershop.services.UserSecurityService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long flowerId, Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());
        cartService.addItemToCart(user, flowerId);
        return String.format("redirect:/flowers/%d", flowerId);
    }   

    @GetMapping
    String getCart(Model model, Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "cart";
    }   

    @PostMapping("/delete")
    public String deleteItemFromCart(@RequestParam Long cartItemId, Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());
        cartItemService.deleteById(cartItemId, user);
        return "redirect:/cart";
    }

    @PostMapping("/increase")
    public String updateItem(@RequestParam Long cartItemId, Principal principal){
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());
        cartItemService.update("add", cartItemId, user);
        return "redirect:/cart";
    }

    @PostMapping("/decrease")
    public String removeItem(@RequestParam Long cartItemId, Principal principal){
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        User user = userSecurityService.getByUsername(userDetails.getUsername());
        cartItemService.update("remove", cartItemId, user);
        return "redirect:/cart";
    }
    @PostMapping("/checkout")
    public String checkout(@RequestParam Long userId){
        User user = userSecurityService.getById(userId).get(0);
        cartItemService.doCheckout(user);
        return "redirect:/cart";
    }
}
