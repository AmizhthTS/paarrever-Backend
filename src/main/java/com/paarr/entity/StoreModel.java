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

    private String address;
    private String areaName;
    private String contactNumber;
    private Boolean active;
}
