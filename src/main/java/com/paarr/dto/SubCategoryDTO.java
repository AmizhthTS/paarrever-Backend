package com.paarr.dto;
import lombok.Data;
@Data
public class SubCategoryDTO {
	
	    private Long id;
	    private String subCategoryName;
	    private String description;
	    private Long categoryId;
	    private ResponseDTO response;
	

}
