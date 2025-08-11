package com.paarr.dto;
import lombok.Data;
import java.util.List;
@Data
public class SubCategoryPageDTO {
	    private int pageNumber;
	    private int listSize;
	    private String searchString;
	    private long count;
	    private int totalPages;
	    private Long categoryId; // filter by parent category
	    private List<SubCategoryDTO> subcategories;
	    private ResponseDTO response;
	

}
