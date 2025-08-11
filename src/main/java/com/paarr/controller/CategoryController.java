package com.paarr.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.paarr.dto.*;
import com.paarr.service.CategoryService;

	@RestController
	@RequestMapping(value = "/category")
	public class CategoryController {

	    @Autowired
	    CategoryService categoryService;

	    @PostMapping(value = "/save", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<ResponseDTO> save(@RequestBody CategoryDTO categoryDTO) {
	        ResponseDTO response = categoryService.save(categoryDTO);
	        return new ResponseEntity<ResponseDTO>(response, HttpStatus.CREATED);
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
