package com.flowershop.myflowershop.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.myflowershop.models.Flower;
import com.flowershop.myflowershop.repos.FlowerRepo;

@Service
public class FlowerService {
    @Autowired
    private FlowerRepo flowerRepository;

    public List<Flower> getFlowers(){
        return flowerRepository.findAll();
    }

    public List<Flower> findById(Long id){
        Optional<Flower> flower = flowerRepository.findById(id);
        return flower.map(Collections::singletonList).orElse(Collections.emptyList());
    }
}
