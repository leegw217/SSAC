package com.ssac.image.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssac.image.dto.Image;

import java.io.*;
import java.util.*;

@Service
public class ImageService {	
	public Image addImageIntoNewsFeed(MultipartFile file) throws Exception {
		if(file.isEmpty()) throw new Exception("파일없음");
		// 파일이름에서 확장자부분만 때기
		String originName = file.getOriginalFilename();
		String ext = "";
		int index = originName.lastIndexOf(".");
		if(index != -1) ext = originName.substring(index);
		// 서버에 저장될 사진이름
		String filename = UUID.randomUUID().toString() + ext;
		// 서버에 저장될 폴더
		String folderRoot = "/home/image/newsfeed/";
		// 폴더에 접근
		File pFile = new File(folderRoot);
		// 폴더에 사진 저장
		file.transferTo(new File(pFile,filename));
		// 폴더에 저장된 사진 불러오기
		InputStream imgStream = new FileInputStream(folderRoot+filename);
		// 이미지 blob형태로 변형
		byte[] blob = IOUtils.toByteArray(imgStream);
		// 폴더 닫기
		imgStream.close();
		// 이미지 객체에 저장
		Image image = new Image();
		image.setFilename(filename);
		image.setBlob(blob);
		return image;
	}
	
	public Image addImageIntoProfile(MultipartFile file) throws Exception {
		if(file.isEmpty()) throw new Exception("파일없음");
		// 파일이름에서 확장자부분만 때기
		String originName = file.getOriginalFilename();
		String ext = "";
		int index = originName.lastIndexOf(".");
		if(index != -1) ext = originName.substring(index);
		// 서버에 저장될 사진이름
		String filename = UUID.randomUUID().toString() + ext;
		// 서버에 저장될 폴더
		String folderRoot = "/home/image/profile/";
		// 폴더에 접근
		File pFile = new File(folderRoot);
		// 폴더에 사진 저장
		file.transferTo(new File(pFile,filename));
		// 폴더에 저장된 사진 불러오기
		InputStream imgStream = new FileInputStream(folderRoot+filename);
		// 이미지 blob형태로 변형
		byte[] blob = IOUtils.toByteArray(imgStream);
		// 폴더 닫기
		imgStream.close();
		// 이미지 객체에 저장
		Image image = new Image();
		image.setFilename(filename);
		image.setBlob(blob);
		return image;
	}
	
	public Image feedFilenameToBlob(String filename) throws Exception {
		if(filename == null || filename.equals("")) return null;
		String folderRoot = "/home/image/newsfeed/";
		InputStream imgStream = new FileInputStream(folderRoot+filename);
	    byte[] imgByteArray = IOUtils.toByteArray(imgStream);
	    imgStream.close();
	    Image image = new Image();
	    image.setFilename(filename);
	    image.setBlob(imgByteArray);
		return image;
	}
	
	public Image profileFilenameToBlob(String filename) throws Exception {
		String folderRoot = "/home/image/profile/";
		InputStream imgStream = new FileInputStream(folderRoot+filename);
	    byte[] imgByteArray = IOUtils.toByteArray(imgStream);
	    imgStream.close();
	    Image image = new Image();
	    image.setFilename(filename);
	    image.setBlob(imgByteArray);
		return image;
	}
}