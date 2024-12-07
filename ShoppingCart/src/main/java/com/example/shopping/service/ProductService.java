package com.example.shopping.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.dto.ImageDto;
import com.example.shopping.dto.ProductDto;
import com.example.shopping.exception.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.model.Image;
import com.example.shopping.model.Product;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ImageRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.request.AddProductRequest;
import com.example.shopping.request.ProductUpdateRequest;


@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ImageRepository imageRepo;
	
	
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepo.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
		productDto.setImages(imageDtos);
		return productDto;
	}
	
	public List<ProductDto> convertToDTOList(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }
	
	//helper function for addProduct
	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category);
	}
	
	public Product addProduct(AddProductRequest request) {
		Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepo.save(newCategory);
				});
		request.setCategory(category);
		return productRepo.save(createProduct(request, category));
	}
	
	public Product getProductById(Long id) {
		return productRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
	}
	
	public void deleteProductById(Long id) {
		Product product = getProductById(id);
		productRepo.delete(product);
	}
	
	//helper function for updateProduct
	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		Category category = categoryRepo.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}
	
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepo.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request))
				.map(productRepo :: save)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}
	
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}
	
	public List<Product> getProductByCategory(String category) {
		return productRepo.findByCategoryName(category);
	}
	
	public List<Product> getProductByName(String name) {
		return productRepo.findByCategoryName(name);
	}
	
	public List<Product> getProductByBrand(String brand) {
		return productRepo.findByBrand(brand);
	}
	
	public List<Product> getProductByCategoryNameAndBrand(String category, String brand) {
		return productRepo.findByCategoryNameAndBrand(category, brand);
	}
	
	public List<Product> getProductByCategoryNameAndName(String category, String name) {
		return productRepo.findByCategoryNameAndName(category, name);
	}
	
	public List<Product> getProductByBrandAndName(String brand, String name) {
		return productRepo.findByBrandAndName(brand, name);
	}
	
	public Long countProductByBrandAndName(String brand, String name) {
		return productRepo.countByBrandAndName(brand, name);
	}
}



