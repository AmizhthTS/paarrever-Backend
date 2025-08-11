package com.paarr.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.paarr.dto.*;
import com.paarr.entity.CategoryModel;
import com.paarr.repository.CategoryRepository;
import com.paarr.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseDTO save(CategoryDTO categoryDTO) {
        CategoryModel categoryModel = null;

        if (categoryDTO.getId() != null && categoryDTO.getId() > 0) {
        	categoryModel = categoryRepository.findByIdAndActive(categoryDTO.getId(), true);
            if (categoryModel == null) {
                throw new RuntimeException("Category not found");
            }
        }

        if (categoryModel == null) {
        	categoryModel = new CategoryModel();
        	categoryModel.setActive(true);
        }

        categoryModel.setCategoryName(categoryDTO.getCategoryName());
        categoryModel.setDescription(categoryDTO.getDescription());
        categoryModel.setImageurl(categoryDTO.getImageurl()); // <-- store image URL

        categoryRepository.save(categoryModel);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage(categoryDTO.getId() != null && categoryDTO.getId() > 0
                ? "Updated Successfully"
                : "Saved Successfully");

        return responseDTO;
    }

    public CategoryPageDTO list(CategoryPageDTO categoryPageDTO) {
        Pageable paging = PageRequest.of(
        		categoryPageDTO.getPageNumber() > 0 ? categoryPageDTO.getPageNumber() - 1 : 0,
        				categoryPageDTO.getListSize() > 0 ? categoryPageDTO.getListSize() : 25,
                Sort.by("categoryName").ascending()
        );

        Page<CategoryModel> page;
        if (categoryPageDTO.getSearchString() != null && !categoryPageDTO.getSearchString().isEmpty()) {
            page = categoryRepository.findByCategoryNameContainsIgnoreCaseAndActive(categoryPageDTO.getSearchString(), true, paging);
        } else {
            page = categoryRepository.findByActive(true, paging);
        }

        List<CategoryDTO> categoryDTOList = page.stream()
                .map(this::constructResponse)
                .collect(Collectors.toList());

        categoryPageDTO.setCategories(categoryDTOList);
        categoryPageDTO.setCount(page.getTotalElements());
        categoryPageDTO.setTotalPages(page.getTotalPages());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage("List Fetched");
        categoryPageDTO.setResponse(responseDTO);

        return categoryPageDTO;
    }

    public CategoryDTO get(long id) {
        CategoryModel categoryModel = categoryRepository.findByIdAndActive(id, true);
        if (categoryModel == null) {
            throw new RuntimeException("Category not found");
        }
        CategoryDTO categoryDTO = constructResponse(categoryModel);

        ResponseDTO response = new ResponseDTO();
        response.setResponseStatus("Success");
        response.setResponseMessage("Record Fetched Successfully");
        categoryDTO.setResponse(response);

        return categoryDTO;
    }

    public ResponseDTO delete(long id) {
        CategoryModel categoryModel = categoryRepository.findByIdAndActive(id, true);
        if (categoryModel == null) {
            throw new RuntimeException("Category not found");
        }
        categoryModel.setActive(false);
        categoryRepository.save(categoryModel);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage("Deleted Successfully");
        
        return responseDTO;
    }

    private CategoryDTO constructResponse(CategoryModel categoryModel) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryModel.getId());
        categoryDTO.setCategoryName(categoryModel.getCategoryName());
        categoryDTO.setDescription(categoryModel.getDescription());
        categoryDTO.setImageurl(categoryModel.getImageurl()); // <-- map image URL
        return categoryDTO;
    }
}
