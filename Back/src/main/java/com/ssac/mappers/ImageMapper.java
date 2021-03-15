package com.ssac.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.ssac.image.dto.Image;

@Mapper
public interface ImageMapper {
	public int insertImageIntoNewsFeed(Image image) throws Exception;
	public Image selectImage(String filename) throws Exception;
	public int deleteImage(String filename) throws Exception;
}
