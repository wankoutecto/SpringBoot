package com.example.shopping.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.shopping.dto.ImageDto;
import com.example.shopping.exception.ResourceNotFoundException;
import com.example.shopping.model.Image;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	@Autowired
	ImageRepository imageRepo;
	@Autowired
	ProductService productService;
	
	public Image getImageById(Long id) {
		return imageRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
	}
	
	public List<Image> findByProductId(Long productId) {
		return Optional.ofNullable(imageRepo.findByProductId(productId))
				.orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + productId));
	}
	
	public void deleteImageById(Long id) {
		imageRepo.findById(id).ifPresentOrElse(imageRepo :: delete, () -> {
			throw new ResourceNotFoundException("No image found with id: " + id);
		});
	}
	
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);
		List<ImageDto> saveImageDto = new ArrayList<>();
		for(MultipartFile file: files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				image.setProduct(product);
				
				String buildDownloadUrl = "/api/v1/images/image/download/";
				String downloadUrl = buildDownloadUrl + image.getId();
				image.setDownloadUrl(downloadUrl);
				Image saveImage = imageRepo.save(image);
				
				saveImage.setDownloadUrl(buildDownloadUrl + saveImage.getId());
				imageRepo.save(saveImage);
				
				ImageDto imageDto = new ImageDto();
				imageDto.setImageId(saveImage.getId());
				imageDto.setImageName(saveImage.getFileName());
				imageDto.setDownloadUrl(saveImage.getDownloadUrl());
				saveImageDto.add(imageDto);
				
			} catch (SQLException | IOException e) {
				throw new RuntimeException(e.getMessage());
			} 
		}
		return saveImageDto;
	}
	
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepo.save(image);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
	}
}
