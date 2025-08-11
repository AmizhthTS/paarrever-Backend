package com.paarr.dto;

import lombok.Data;
import java.util.List;

@Data
public class StorePageDTO {
    private int pageNumber;
    private int listSize;
    private String searchString;
    private long count;
    private int totalPages;
    private List<StoreDTO> stores;
    private ResponseDTO response;
}
