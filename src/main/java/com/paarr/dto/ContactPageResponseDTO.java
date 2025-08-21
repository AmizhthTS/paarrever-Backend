package com.paarr.dto;
import lombok.Data;
import java.util.List;

@Data
public class ContactPageResponseDTO {
    private int pageNumber;
    private int listSize;
    private String searchString;
    private long count;       // number of items in this page
    private int totalPages;   // total available pages
    private List<ContactListResponseDTO> data; // actual records
}
