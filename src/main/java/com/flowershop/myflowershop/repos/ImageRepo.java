package com.flowershop.myflowershop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowershop.myflowershop.models.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long>{
    
}
