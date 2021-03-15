package com.ssac.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssac.notice.dto.Notice;
import com.ssac.notice.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@ApiOperation(value = "알림 불러오기(10개)", notes = "입력 : userid")
	@GetMapping("/noticeList")
	public ResponseEntity<?> getNoticeList(@RequestParam String userid) throws Exception {
		try {
			List<Notice> notices = noticeService.listNotice(userid);
			return new ResponseEntity<>(notices, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.OK);
	}
}
