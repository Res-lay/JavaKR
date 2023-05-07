package com.flowershop.myflowershop.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.myflowershop.models.Cart;
import com.flowershop.myflowershop.models.CartItem;
import com.flowershop.myflowershop.models.Flower;
import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.repos.CartItemRepo;
import com.flowershop.myflowershop.repos.CartRepo;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartRepo cartRepo;

    void save(CartItem cartItem){
        cartItemRepo.save(cartItem);
    }

    public CartItem getByCart(Cart cart, Long flowerID){
        return cartItemRepo.findByCart(cart).stream()
        .filter(item -> item.getFlower().getId().equals(flowerID))
        .findFirst().orElse(null);
    }

    public CartItem getByFlower(Flower flower){
        return cartItemRepo.findByFlower(flower);
    }
    
    public void deleteById(Long id, User user){
        Cart userCart = user.getCart();
        CartItem cartItem = cartItemRepo.getById(id);
        userCart.setTotalPrice(userCart.getTotalPrice() - cartItem.getQuantity() * cartItem.getFlower().getPrice());
        cartRepo.save(userCart);
        cartItemRepo.deleteById(id);
    }

    public void update(String operation, Long itemId, User user){
        CartItem cartItem = cartItemRepo.getById(itemId);
        Cart userCart = user.getCart();
        if (operation == "add"){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepo.save(cartItem);
            
            userCart.setTotalPrice(userCart.getTotalPrice() + cartItem.getFlower().getPrice());
            cartRepo.save(userCart);
        }
        else if(operation == "remove"){
            if(cartItem.getQuantity() > 1){
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemRepo.save(cartItem);

                userCart.setTotalPrice(userCart.getTotalPrice() - cartItem.getFlower().getPrice());
                cartRepo.save(userCart);
            }
            
            else if (cartItem.getQuantity() == 1){
                cartItemRepo.deleteById(itemId);
            }
        }
    }

    public void doCheckout(User user){
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        Iterator<CartItem> iter = cartItems.iterator();
        while(iter.hasNext()){
            CartItem item = iter.next();
            cartItemRepo.deleteById(item.getId());
            iter.remove();
        }
        cart.setTotalPrice(0);
        cartRepo.save(cart);
    }

}
