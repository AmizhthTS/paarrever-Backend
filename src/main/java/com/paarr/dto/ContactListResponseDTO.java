package com.paarr.dto;

import java.util.List;

import lombok.Data;

@Data
public class ContactListResponseDTO {
    private Long id;
    private String fullName;
    private String emailAddress;
    private String subject;
    private String message;
    private String phoneNumber;
    private boolean Checkbox;
//	    private int pageNumber;
//	    private int listSize;
//	    private String searchString;
//	    private long count;       // number of items in this page
//	    private int totalPages;   // total available pages
//	    private List<ContactListResponseDTO> data; // actual records
    
    
}
