package com.example.shopping.controller;

import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.shopping.dto.ImageDto;
import com.example.shopping.exception.ResourceNotFoundException;
import com.example.shopping.model.Image;
import com.example.shopping.response.ApiResponse;
import com.example.shopping.service.ImageService;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //used for injection instead of using Autowired
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	private final ImageService imageService; //add private final to inject it
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
		try {
			List<ImageDto> imageDtos = imageService.saveImages(files, productId);
			return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
		} catch (Exception e) {
			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
		}
	}
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId){
		Image image = imageService.getImageById(imageId);
		ByteArrayResource resource = null;
		try {
			resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
	    		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
	    		.body(resource);
	}
	
	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
		try {
			Image image = imageService.getImageById(imageId);
			if(image != null) {
				imageService.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Upload success!", null));
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", HttpStatus.INTERNAL_SERVER_ERROR)); 
	}
	
	@DeleteMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> daleteImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
		try {
			Image image = imageService.getImageById(imageId);
			if(image != null) {
				imageService.deleteImageById(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete success!", null));
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", HttpStatus.INTERNAL_SERVER_ERROR)); 
	}
	
}








