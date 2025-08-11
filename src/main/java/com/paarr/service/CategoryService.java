package com.paarr.service;
import com.paarr.dto.CategoryDTO;

import com.paarr.dto.CategoryPageDTO;
import com.paarr.dto.ResponseDTO;

public interface CategoryService {
	public ResponseDTO save(CategoryDTO categoryDTO);
	public CategoryPageDTO list(CategoryPageDTO categoryPageDTO);
	public CategoryDTO get(long id);
	public ResponseDTO delete(long id);

}
