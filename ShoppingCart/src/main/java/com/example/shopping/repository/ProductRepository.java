package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCategoryName(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByCategoryNameAndBrand(String category, String brand);

	List<Product> findByCategoryNameAndName(String category, String name);

	List<Product> findByBrandAndName(String brand, String name);

	Long countByBrandAndName(String brand, String name);
	
}
