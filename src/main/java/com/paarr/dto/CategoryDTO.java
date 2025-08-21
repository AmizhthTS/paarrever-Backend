package com.paarr.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	    private Long id;
	    private String categoryName;
	    private String description;
	    private byte[] image;
	    private String imageName;
	    private int sequence;
	    //private String imageUrl;
	    //private ResponseDTO response;
	    private Boolean active;
	}


