package com.paarr.dto;



import java.time.LocalTime;

import lombok.Data;

//@Data
//public class StoreDTO {
//    private Long id;
//    private String storeName;
//    private String address;
//    private String areaName;
//    private String contactNumber;
//    private ResponseDTO response;
//}
@Data
public class StoreDTO {
    private Long id;
    private String storeName;
    private String address;
    private String mainArea;
    private String subArea;
    private String contactNumber;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String mapLink;
	private String time;
	
}
