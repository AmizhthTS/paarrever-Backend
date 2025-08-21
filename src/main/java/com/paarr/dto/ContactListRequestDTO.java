package com.paarr.dto;

import lombok.Data;

@Data
public class ContactListRequestDTO {
    private int pageNumber;
    private int listSize;
    private String searchString;
}
