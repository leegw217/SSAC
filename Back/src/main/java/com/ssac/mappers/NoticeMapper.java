package com.ssac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssac.notice.dto.Notice;

@Mapper
public interface NoticeMapper {
	public List<Notice> selectNotice(String userid) throws Exception;
	public int insertNotice(Notice notice) throws Exception;
}
