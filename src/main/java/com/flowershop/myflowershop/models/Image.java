package com.flowershop.myflowershop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PATH")
    private String path;

    @Column(name = "SIZE")
    private Long size;

    @Column(name = "ISPREVIEWIMAGE")
    private boolean isPreviewImage;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Flower flower;

}
    // @Override
    // public String toString() {
    //     return "Image{" +
    //             "id=" + id +
    //             "name=" + name +
    //             "size=" + size +
    //             "type=" + type +
    //             ", path='" + path + '\'' +
    //             ", isPreviewImage=" + isPreviewImage +
    //             '}';
    // }