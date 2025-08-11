package com.paarr.dto;



import lombok.Data;

@Data
public class StoreDTO {
    private Long id;
    private String storeName;
    private String address;
    private String areaName;
    private String contactNumber;
    private ResponseDTO response;
}
