package com.flowershop.myflowershop.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "flowers")
public class Flower implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "COMPOSITION")
    private String composition;

    @Column(name = "DIMENSION")
    private String dimensions;

    @Column(name = "WEIGHT")
    private String weight;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "DURABILITY")
    private String durability;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "flower")
    private List<Image> images; 

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "flower")
    private List<CartItem> cartItems;

}
