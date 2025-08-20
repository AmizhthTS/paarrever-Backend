package com.paarr.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.paarr.dto.ResponseDTO;
import com.paarr.dto.SubCategoryDTO;
import com.paarr.dto.SubCategoryPageDTO;
import com.paarr.service.SubCategoryService;

	@RestController
	@RequestMapping(value = "/subcategory")
	@CrossOrigin(origins = "*")
	public class SubCategoryController {

	    @Autowired
	    SubCategoryService subCategoryService;

	    @PostMapping(value = "/save", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<ResponseDTO> save(@RequestBody SubCategoryDTO subCategoryDTO) {
	        ResponseDTO response = subCategoryService.save(subCategoryDTO);
	        return new ResponseEntity<ResponseDTO>(response, HttpStatus.CREATED);
	    }
 
	    @PostMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<SubCategoryPageDTO> list(@RequestBody SubCategoryPageDTO subCategoryPageDTO) {
	        subCategoryPageDTO = subCategoryService.list(subCategoryPageDTO);
	        return new ResponseEntity<SubCategoryPageDTO>(subCategoryPageDTO, HttpStatus.OK);
	    }

	    @GetMapping(value = "/get")
	    public ResponseEntity<SubCategoryDTO> get(@RequestParam long id) {
	        SubCategoryDTO subCategoryDTO = subCategoryService.get(id);
	        return new ResponseEntity<SubCategoryDTO>(subCategoryDTO, HttpStatus.OK);
	    }
	    @GetMapping("/getby-category/{categoryId}")
	    public ResponseEntity<List<SubCategoryDTO>> getByCategory(@PathVariable Long categoryId) {
	        List<SubCategoryDTO> subCategories = subCategoryService.getByCategory(categoryId);
	        return ResponseEntity.ok(subCategories);
	    }

	    @DeleteMapping(value = "/delete")
	    public ResponseEntity<ResponseDTO> delete(@RequestParam long id) {
	        ResponseDTO responseDTO = subCategoryService.delete(id);
	        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	    }
	

}
