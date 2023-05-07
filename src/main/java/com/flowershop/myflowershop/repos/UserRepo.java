package com.flowershop.myflowershop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowershop.myflowershop.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
