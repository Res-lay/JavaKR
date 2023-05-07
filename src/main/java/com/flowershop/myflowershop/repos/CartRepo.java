package com.flowershop.myflowershop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowershop.myflowershop.models.Cart;
import com.flowershop.myflowershop.models.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{
    Cart findByUser(User User);
}
