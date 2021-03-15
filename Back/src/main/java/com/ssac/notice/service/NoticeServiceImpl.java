package com.ssac.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssac.mappers.NoticeMapper;
import com.ssac.notice.dto.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeMapper mapper;
	
	@Override
	public List<Notice> listNotice(String userid) throws Exception {
		return mapper.selectNotice(userid);
	}
	
	@Override
	public int writeNotice(Notice notice) throws Exception {
		return mapper.insertNotice(notice);
	}
}
