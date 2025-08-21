package com.paarr.service.implementation;
import com.paarr.dto.*;
import com.paarr.entity.CategoryModel;
import com.paarr.entity.SubCategoryModel;
import com.paarr.repository.CategoryRepository;
import com.paarr.repository.SubCategoryRepository;
import com.paarr.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

	@Service
	public class SubCategoryServiceImpl implements SubCategoryService {

	    @Autowired
	    SubCategoryRepository subCategoryRepository;

	    @Autowired
	    CategoryRepository categoryRepository;

	    
	    public ResponseDTO save(SubCategoryDTO subCategoryDTO) {
	        SubCategoryModel subCategoryModel = null;

	        // Update mode
	        if (subCategoryDTO.getId() != null && subCategoryDTO.getId() > 0) {
	            subCategoryModel = subCategoryRepository.findByIdAndActive(subCategoryDTO.getId(), true);
	            if (subCategoryModel == null) {
	                throw new RuntimeException("SubCategory not found");
	            }
	        }

	        // Create mode
	        if (subCategoryModel == null) {
	            subCategoryModel = new SubCategoryModel();
	            subCategoryModel.setActive(true);
	        }

	        CategoryModel categoryModel = categoryRepository.findByIdAndActive(subCategoryDTO.getCategoryId(), true);
	        if (categoryModel == null) {
	            throw new RuntimeException("Category not found");
	        }

	        subCategoryModel.setSubCategoryName(subCategoryDTO.getSubCategoryName());
	        subCategoryModel.setDescription(subCategoryDTO.getDescription());
	        subCategoryModel.setCategory(categoryModel);

	        subCategoryRepository.save(subCategoryModel);

	        ResponseDTO responseDTO = new ResponseDTO();
	        responseDTO.setResponseStatus("Success");
	        responseDTO.setResponseMessage(subCategoryDTO.getId() != null && subCategoryDTO.getId() > 0 ? "Updated Successfully" : "Saved Successfully");

	        return responseDTO;
	    }

	   
	    public SubCategoryPageDTO list(SubCategoryPageDTO subCategoryPageDTO) {
	        Pageable paging = PageRequest.of(
	                subCategoryPageDTO.getPageNumber() > 0 ? subCategoryPageDTO.getPageNumber() - 1 : 0,
	                subCategoryPageDTO.getListSize() > 0 ? subCategoryPageDTO.getListSize() : 25,
	                Sort.by("subCategoryName").ascending()
	        );

	        Page<SubCategoryModel> page;
	        if (subCategoryPageDTO.getSearchString() != null && !subCategoryPageDTO.getSearchString().isEmpty()) {
	            page = subCategoryRepository.findBySubCategoryNameContainsIgnoreCaseAndActive(
	                    subCategoryPageDTO.getSearchString(),
	                    true,
	                    paging
	            );
	        } else {
	            page = subCategoryRepository.findByActive(true, paging);
	        }

	        List<SubCategoryDTO> subCategoryDTOList = page.stream()
	                .map(this::constructResponse)
	                .collect(Collectors.toList());

	        subCategoryPageDTO.setSubcategories(subCategoryDTOList);
	        subCategoryPageDTO.setCount(page.getTotalElements());
	        subCategoryPageDTO.setTotalPages(page.getTotalPages());

	        ResponseDTO response = new ResponseDTO();
	        response.setResponseStatus("Success");
	        response.setResponseMessage("List Fetched");
	        subCategoryPageDTO.setResponse(response);

	        return subCategoryPageDTO;
	    }

	   
	    public SubCategoryDTO get(long id) {
	        SubCategoryModel subCategoryModel = subCategoryRepository.findByIdAndActive(id, true);
	        if (subCategoryModel == null) {
	            throw new RuntimeException("SubCategory not found");
	        }
	        SubCategoryDTO subCategoryDTO = constructResponse(subCategoryModel);

	        ResponseDTO responseDTO = new ResponseDTO();
	        responseDTO.setResponseStatus("Success");
	        responseDTO.setResponseMessage("Record Fetched Successfully");
	       

	        return subCategoryDTO;
	    }

	  
	    public ResponseDTO delete(long id) {
	        SubCategoryModel subCategoryModel = subCategoryRepository.findByIdAndActive(id, true);
	        if (subCategoryModel == null) {
	            throw new RuntimeException("SubCategory not found");
	        }
	        subCategoryModel.setActive(false);
	        subCategoryRepository.save(subCategoryModel);

	        ResponseDTO responseDTO = new ResponseDTO();
	        responseDTO.setResponseStatus("Success");
	        responseDTO.setResponseMessage("Deleted Successfully");

	        return responseDTO;
	    }

	   
	    public List<SubCategoryDTO> getByCategory(Long categoryId) {
	        return subCategoryRepository.findByCategoryIdAndActive(categoryId, true)
	                .stream()
	                .map(this::constructResponse)
	                .collect(Collectors.toList());
	    }

	    private SubCategoryDTO constructResponse(SubCategoryModel model) {
	        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
	        subCategoryDTO.setId(model.getId());
	        subCategoryDTO.setSubCategoryName(model.getSubCategoryName());
	        subCategoryDTO.setDescription(model.getDescription());
	        subCategoryDTO.setCategoryId(model.getCategory().getId());
	        return subCategoryDTO;
	    }
	

}
