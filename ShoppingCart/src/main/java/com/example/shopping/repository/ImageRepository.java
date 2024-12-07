package com.example.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByProductId(Long productId);

}
