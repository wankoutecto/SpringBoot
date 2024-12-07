package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String name);

}
