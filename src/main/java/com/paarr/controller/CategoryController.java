package com.paarr.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.paarr.dto.*;
import com.paarr.entity.CategoryModel;
import com.paarr.service.CategoryService;

	@RestController
	@RequestMapping(value = "/category")
	@CrossOrigin(origins = "*")
	public class CategoryController {

	    @Autowired
	    CategoryService categoryService;

//	    @PostMapping(value = "/save", produces = { MediaType.APPLICATION_JSON_VALUE })
//	    public ResponseEntity<ResponseDTO> save(@RequestBody CategoryDTO categoryDTO) {
//	        ResponseDTO response = categoryService.save(categoryDTO);
//	        return new ResponseEntity<ResponseDTO>(response, HttpStatus.CREATED);
//	    }
	    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<ResponseDTO> save(
	            @RequestParam(required = false) Long id,
	            @RequestParam String name,
	            @RequestParam String description,
	            @RequestParam(required = false) MultipartFile imageFile) {

	       // CategoryDTO dto = new CategoryDTO(id, name, description, null, imageFile);
	    	CategoryDTO dto = new CategoryDTO();
	        dto.setId(id);
	        dto.setCategoryName(name);
	        dto.setDescription(description);
	        dto.setImageFile(imageFile);

	        ResponseDTO response = categoryService.save(dto);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
//	        ResponseDTO<CategoryModel> response = categoryService.save(dto);
//	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    

	    @PostMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<CategoryPageDTO> list(@RequestBody CategoryPageDTO categoryPageDTO) {
	        categoryPageDTO = categoryService.list(categoryPageDTO);
	        return new ResponseEntity<CategoryPageDTO>(categoryPageDTO, HttpStatus.OK);
	    }

	    @GetMapping(value = "/get")
	    public ResponseEntity<CategoryDTO> get(@RequestParam long id) {
	        CategoryDTO categoryDTO = categoryService.get(id);
	        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	    }

	    @DeleteMapping(value = "/delete")
	    public ResponseEntity<ResponseDTO> delete(@RequestParam long id) {
	        ResponseDTO responseDTO = categoryService.delete(id);
	        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	    }
	

}
