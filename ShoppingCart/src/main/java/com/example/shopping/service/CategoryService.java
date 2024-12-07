package com.example.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.exception.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepo;
	
	public Category getCategoryById(Long id) {
		return categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}
	
	public Category getCategoryByName(String name) {
		return categoryRepo.findByName(name);
	}
	
	public List<Category> getALlCategories() {
		return categoryRepo.findAll();
	}
	
	public Category addCategory(Category category) {
	    Category existingCategory = categoryRepo.findByName(category.getName());
	    if (existingCategory != null) {
	        throw new ResourceNotFoundException("Category already exists!");
	    }
	    return categoryRepo.save(category);
	}

	
	public Category updateCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id))
				.map(existingCategory -> {
					existingCategory.setName(category.getName());
					return categoryRepo.save(existingCategory);
				}).orElseThrow(() -> {
					throw new ResourceNotFoundException("Category not found!");});
	}
	
	public void deleteCategoryById(Long id) {
		categoryRepo.findById(id).ifPresentOrElse(categoryRepo :: delete, () -> {
			throw new ResourceNotFoundException("Category not found!");
		});
	}
}
 