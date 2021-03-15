package com.ssac.image.controller;

import com.ssac.image.dto.Image;
import com.ssac.image.service.ImageService;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/file")
public class ImageController {
	@Autowired
    private ImageService imageService;
	
	@ApiOperation(value = "뉴스피드에 이미지 넣기", notes = "입력 : file(MultipartFile형식의 이미지)")
	@PostMapping("/newsfeedImageAdd")
	public ResponseEntity<?> addImageNewsFeed(@RequestParam MultipartFile file) throws Exception {
		try {
			Image image = imageService.addImageIntoNewsFeed(file);			
			System.out.println("뉴스피드 이미지 넣기 : "+file.getOriginalFilename()+" "+image.getFilename());
			return new ResponseEntity<>(image, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.OK);
	}
	
	@ApiOperation(value = "프로필에 이미지 넣기", notes = "입력 : file(MultipartFile형식의 이미지)")
	@PostMapping("/profileImageAdd")
	public ResponseEntity<?> addImageProfile(@RequestParam MultipartFile file) throws Exception {
		try {
			System.out.println("프로필 이미지 넣기");
			return new ResponseEntity<>(imageService.addImageIntoProfile(file), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.OK);
	}
}