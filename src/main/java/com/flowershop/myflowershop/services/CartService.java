package com.flowershop.myflowershop.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.myflowershop.models.Cart;
import com.flowershop.myflowershop.models.CartItem;
import com.flowershop.myflowershop.models.Flower;
import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.repos.CartRepo;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private CartItemService cartItemService;

    public Cart findByUser(User user) {
        return cartRepo.findByUser(user);
    }

    public List<Cart> getById(Long id) {
        Optional<Cart> cart = cartRepo.findById(id);
        return cart.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    public void addItemToCart(User user, Long flowerId) {
        Flower flower = flowerService.findById(flowerId).get(0);
        CartItem cartItem = cartItemService.getByCart(user.getCart(), flowerId);
        Cart userCart = user.getCart();
        userCart.setTotalPrice(userCart.getTotalPrice() + flower.getPrice());
        cartRepo.save(userCart);
        if (cartItem != null && cartItem.getFlower().getId() == flowerId){
            System.out.println(cartItem.getId());
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemService.save(cartItem);
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setQuantity(1);
            newItem.setCart(user.getCart());
            newItem.setFlower(flower);
            cartItemService.save(newItem);
        }
    }
}
