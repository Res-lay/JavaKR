package com.flowershop.myflowershop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowershop.myflowershop.models.Flower;

@Repository
public interface FlowerRepo extends JpaRepository<Flower, Long>{
    
}
