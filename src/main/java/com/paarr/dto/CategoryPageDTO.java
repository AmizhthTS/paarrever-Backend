package com.paarr.dto;
import lombok.Data;
import java.util.List;

@Data
public class CategoryPageDTO {
	   private int pageNumber;
	     private int listSize;
	    private String searchString;
	    private long count;
	    private int totalPages;
	   private long categoryId;
	    private List<CategoryDTO> categories;
	    private ResponseDTO response;
}
