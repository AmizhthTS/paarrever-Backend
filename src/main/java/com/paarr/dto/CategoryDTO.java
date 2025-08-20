package com.paarr.dto;
import org.springframework.web.multipart.MultipartFile;

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
	    private String imageUrl;
	    private MultipartFile imageFile;
	    private ResponseDTO response;
	}


