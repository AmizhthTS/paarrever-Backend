package com.paarr.service.implementation;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.paarr.dto.*;
//import com.paarr.entity.CategoryModel;
//import com.paarr.repository.CategoryRepository;
//import com.paarr.service.CategoryService;
//
//import io.jsonwebtoken.io.IOException;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//package com.paarr.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.paarr.dto.*;
import com.paarr.entity.CategoryModel;
import com.paarr.repository.CategoryRepository;
import com.paarr.service.CategoryService;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    private S3Client s3Client;

    private final String BUCKET = "paarr-dev-doc";// change to your S3 bucket
    private static final String REGION = "ap-south-1";

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
        
        // Handle image upload
        MultipartFile imageFile = categoryDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = uploadToS3(imageFile);
            categoryModel.setImageurl(imageUrl);
        } else if (categoryDTO.getImageUrl() != null) {
            // Keep existing URL if provided
            categoryModel.setImageurl(categoryDTO.getImageUrl());
        }
        //categoryModel.setImageurl(categoryDTO.getImageurl()); // <-- store image URL

        categoryRepository.save(categoryModel);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseStatus("Success");
        responseDTO.setResponseMessage(categoryDTO.getId() != null && categoryDTO.getId() > 0
                ? "Updated Successfully"
                : "Saved Successfully");
        responseDTO.setResponse(categoryModel);

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
        categoryDTO.setImageUrl(categoryModel.getImageurl()); // <-- map image URL
        return categoryDTO;
    }
    private String uploadToS3(MultipartFile file) {
        try {
            String key = "category/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(key)
                    //.acl("public-read")
                    //.acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putOb, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            //return "https://" + BUCKET + ".s3.amazonaws.com/" + key;
            return "https://" + BUCKET + ".s3." + REGION + ".amazonaws.com/" + key;

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }
}
