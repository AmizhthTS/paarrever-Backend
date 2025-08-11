package com.paarr.service;

import com.paarr.dto.SubCategoryDTO;
import com.paarr.dto.SubCategoryPageDTO;
import com.paarr.dto.ResponseDTO;
import java.util.List;
public interface SubCategoryService {
	
	   public  ResponseDTO save(SubCategoryDTO subCategoryDTO);
	   public SubCategoryPageDTO list(SubCategoryPageDTO subCategoryPageDTO);
	  public  SubCategoryDTO get(long id);
	   public ResponseDTO delete(long id);
	    List<SubCategoryDTO> getByCategory(Long categoryId);
	

}
