package com.example.shopping.request;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.shopping.model.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class AddProductRequest {
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
}
