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
import org.springframework.stereotype.Service;
import com.paarr.dto.*;
import com.paarr.entity.CategoryModel;
import com.paarr.exception.EcosystemException;
import com.paarr.exception.ErrorEnum;
import com.paarr.repository.CategoryRepository;
import com.paarr.service.CategoryService;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.paarr.util.AWSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	private AWSUtil awsUtil;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private S3Client s3Client;

//    private final String BUCKET = "paarr-dev-doc";// change to your S3 bucket
//    private static final String REGION = "ap-south-1";

	public ResponseDTO save(CategoryDTO categoryDTO) {
		CategoryModel categoryModel = null;
		String image = null;

		if (categoryDTO.getImage() != null && categoryDTO.getImage().length > 0) {

			if (!Arrays.equals(categoryDTO.getImage(), "something".getBytes())) {
				ByteArrayInputStream targetStream = new ByteArrayInputStream(categoryDTO.getImage());

				try {
					if (!awsUtil.checkFileSize(targetStream))
						throw new EcosystemException(ErrorEnum.INVALID_FILE_SIZE);
				} catch (Exception e) {
					throw new EcosystemException(ErrorEnum.INVALID_FILE_SIZE);
				}

				if (categoryDTO.getImageName().isEmpty() || !categoryDTO.getImageName().contains("."))
					throw new EcosystemException(ErrorEnum.INVALID_FILE_NAME);

				String fullFileName = categoryDTO.getImageName();
				String[] fileArray = fullFileName.split("[.]");
				String fileName = fileArray[0];
				String fileFormat = fileArray[1];
				try {
					image = awsUtil.saveFile(targetStream, fileName, fileFormat, "category");
				} catch (Exception e) {
					throw new EcosystemException(ErrorEnum.FILE_UPLOAD_FAILED);
				}

				if (image.isEmpty()) {
					throw new EcosystemException(ErrorEnum.FILE_UPLOAD_FAILED);
				} else {
					if (categoryDTO.getId() != null) {
						CategoryModel existingCategory = categoryRepository.findById(categoryDTO.getId()).orElse(null);
						if (existingCategory != null && existingCategory.getImage() != null
								&& !existingCategory.getImage().isEmpty()) {
							try {
								awsUtil.copyAndTrashBucketObject(existingCategory.getImage(),
										existingCategory.getImage(), "category");
							} catch (Exception e) {
								logger.warn("File Not Found to remove from AWS :: " + existingCategory.getImage());
							}
						}
					}
				}
			} else {
				if (categoryDTO.getId() != null) {
					CategoryModel existingCategory = categoryRepository.findById(categoryDTO.getId()).orElse(null);
					if (existingCategory != null) {
						image = existingCategory.getImage();
					}
				}
			}
		}

		// Save or update category
		if (categoryDTO.getId() != null && categoryDTO.getId() > 0) {
			categoryModel = categoryRepository.findById(categoryDTO.getId()).orElse(new CategoryModel());
		} else {
			categoryModel = new CategoryModel();
			categoryModel.setActive(true); 
			
		}

		categoryModel.setCategoryName(categoryDTO.getCategoryName());
		categoryModel.setDescription(categoryDTO.getDescription());
		categoryModel.setImage(image);

		categoryModel = categoryRepository.save(categoryModel);
		
		//CategoryDTO ResponseCategoryDTO = constructResponse(categoryModel);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResponseStatus("Success");
		responseDTO.setResponseMessage(
				categoryDTO.getId() != null && categoryDTO.getId() > 0 ? "Updated Successfully" : "Saved Successfully");
		responseDTO.setResponse(categoryModel);

		return responseDTO;
	}

	public CategoryPageDTO list(CategoryPageDTO categoryPageDTO) {
		Pageable paging = PageRequest.of(categoryPageDTO.getPageNumber() > 0 ? categoryPageDTO.getPageNumber() - 1 : 0,
				categoryPageDTO.getListSize() > 0 ? categoryPageDTO.getListSize() : 25,
				Sort.by("categoryName").ascending());

		Page<CategoryModel> page;
		if (categoryPageDTO.getSearchString() != null && !categoryPageDTO.getSearchString().isEmpty()) {
			page = categoryRepository.findByCategoryNameContainsIgnoreCaseAndActive(categoryPageDTO.getSearchString(),
					true, paging);
		} else {
			page = categoryRepository.findByActive(true, paging);
		}

		List<CategoryDTO> categoryDTOList = page.stream().map(this::constructResponse).collect(Collectors.toList());

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

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResponseStatus("Success");
		responseDTO.setResponseMessage("Record Fetched Successfully");
		//categoryDTO.setResponse(responseDTO);

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
		categoryDTO.setActive(categoryModel.getActive());
		if (categoryModel.getImage() != null && !categoryModel.getImage().isEmpty()) {
			String fileName = categoryModel.getImage();
 
			String preSignedFileUrl = awsUtil.getpreSignedFile(5, fileName, "category");
			if (!preSignedFileUrl.isEmpty()) {
				categoryDTO.setImageName(preSignedFileUrl);
			} else {
				logger.warn("Get Image File URL Failed " + fileName);
			}
		}
		return categoryDTO;
	}

}
