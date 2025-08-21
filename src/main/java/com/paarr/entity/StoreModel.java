package com.paarr.entity;


import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name", nullable = false)
    private String storeName;
    
    @Column(name="address",nullable=false)
    private String address;
    
    @Column(name="main_area",nullable=false)
    private String mainArea;
    
    @Column(name="sub_area",nullable=false)
    private String subArea;
    
    @Column(name="contact_number",nullable=false)
    private String contactNumber;
    
    private LocalTime OpeningTime;
    private LocalTime ClosingTime;
    
    @Column(name="Map_Link",nullable=true)
    private String mapLink;
    
    
    @Column(name="active", nullable=false)
    private Boolean active;
    
    
   
}
