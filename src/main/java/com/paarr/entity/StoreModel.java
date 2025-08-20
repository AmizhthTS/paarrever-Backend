package com.paarr.entity;


import jakarta.persistence.*;
import lombok.*;

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
    
    @Column(name="Opening_Time",nullable=false)
    private String OpeningTime;
    
    @Column(name="Closing_Time",nullable=false)
    private String ClosingTime;
    @Column(name="active", nullable=false)
    private Boolean active;
    
    
   
}
