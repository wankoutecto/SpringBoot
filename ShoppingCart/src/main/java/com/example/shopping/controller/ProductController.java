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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.dto.ProductDto;
import com.example.shopping.exception.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.model.Product;
import com.example.shopping.request.AddProductRequest;
import com.example.shopping.request.ProductUpdateRequest;
import com.example.shopping.response.ApiResponse;
import com.example.shopping.service.CategoryService;
import com.example.shopping.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //used for injection instead of using Autowired
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	private final ProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProduct(){
		List<Product> products = productService.getAllProduct();
		List<ProductDto> convertedProduct = productService.convertToDTOList(products);
		return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
	}
	
	@GetMapping("/product/{prductId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
		try {
			Product product = productService.getProductById(productId);
			ProductDto productDto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("success", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product theProduct = productService.addProduct(product);
			ProductDto productDto = productService.convertToDto(theProduct);
			return ResponseEntity.ok(new ApiResponse("add product success", productDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/product/{productId}/update")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
		try {
			Product theProduct = productService.updateProduct(request, productId);
			ProductDto productDto = productService.convertToDto(theProduct);
			return ResponseEntity.ok(new ApiResponse("update product success", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));		}
	}
	
	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("update product success", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
		try {
			List<Product> products = productService.getProductByBrandAndName(brandName, productName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));	
			}
			List<ProductDto> convertedProduct = productService.convertToDTOList(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
		}
		
	}
	
	@GetMapping("/products/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
		try {
			List<Product> products = productService.getProductByCategoryNameAndBrand(category, brand);	
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));	
			}
			List<ProductDto> convertedProduct = productService.convertToDTOList(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
		}
		
	}
	
	@GetMapping("/products/{name}/products")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> products = productService.getProductByName(name);	
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));	
			}
			List<ProductDto> convertedProduct = productService.convertToDTOList(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
		}
		
	}
	
	@GetMapping("/products/by-brand")
	public ResponseEntity<ApiResponse>findProductByBrand(@RequestParam String brand){
		try {
			List<Product> products = productService.getProductByBrand(brand);	
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));	
			}
			List<ProductDto> convertedProduct = productService.convertToDTOList(products);
			return ResponseEntity.ok(new ApiResponse("success",convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
		}	
	}
	
	@GetMapping("/products/{category}/all/products")
	public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
		try {
			List<Product> products = productService.getProductByCategory (category);	
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));	
			}
			List<ProductDto> convertedProduct = productService.convertToDTOList(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
		}
		
	}
	
	@GetMapping("/products/count/by-brand/and-name")
	public  ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
		try {
			var productCount = productService.countProductByBrandAndName(brand, name);		
			return ResponseEntity.ok(new ApiResponse("Product count", productCount));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}
	
}
