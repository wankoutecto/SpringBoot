package com.example.shopping.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.exception.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.response.ApiResponse;
import com.example.shopping.service.CategoryService;
import com.example.shopping.service.ImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //used for injection instead of using Autowired
@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {
	private final CategoryService categoryService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getALlCategories();
			return ResponseEntity.ok(new ApiResponse("Found category", categories));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategories(@RequestBody Category name){
		try {
			Category theCategory = categoryService.addCategory(name);
			return ResponseEntity.ok(new ApiResponse("success!", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/category/{id}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
		try {
			Category theCategory = categoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("found!", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/category/{name}/category")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		try {
			Category theCategory = categoryService.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("found!", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("category/{id}/delete")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
		try {
			categoryService.deleteCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("found!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("category/{id}/update")
	public ResponseEntity<ApiResponse> updateCategories(@PathVariable Long id, @RequestBody Category category){
		try {
			Category updateCategory = categoryService.updateCategory(category, id);
			return ResponseEntity.ok(new ApiResponse("update success!", updateCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));		}
	}
}











