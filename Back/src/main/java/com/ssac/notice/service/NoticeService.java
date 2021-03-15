package com.ssac.notice.service;

import java.util.List;

import com.ssac.notice.dto.Notice;

public interface NoticeService {

	List<Notice> listNotice(String userid) throws Exception;

	int writeNotice(Notice notice) throws Exception;

}