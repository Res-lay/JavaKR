package com.flowershop.myflowershop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowershop.myflowershop.models.Cart;
import com.flowershop.myflowershop.models.CartItem;
import com.flowershop.myflowershop.models.Flower;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long>{
    List<CartItem> findByCart(Cart cart);
    CartItem findByFlower(Flower flower);
}
