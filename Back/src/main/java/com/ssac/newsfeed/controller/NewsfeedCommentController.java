package com.ssac.newsfeed.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssac.image.service.ImageService;
import com.ssac.newsfeed.dto.NewsFeed;
import com.ssac.newsfeed.dto.NewsFeedComment;
import com.ssac.newsfeed.service.NewsFeedService;
import com.ssac.notice.dto.Notice;
import com.ssac.notice.service.NoticeService;
import com.ssac.user.dto.User;
import com.ssac.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/newsfeedcomment")
public class NewsfeedCommentController {
	@Autowired
	private NewsFeedService newsfeedService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private NoticeService noticeService;
	
	@ApiOperation(value = "뉴스피드 댓글 조회", notes = "입력 : 게시글번호(no)")
	@GetMapping("/commentList")
	public ResponseEntity<?> getAllCommentList(@RequestParam int no) throws Exception {
		// 뉴스피드 댓글 불러오기
		// 입력 : 게시글 번호
		// 출력 : 뉴스피드 댓글
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			System.out.println("뉴스피드 댓글 불러오기 : "+no);
			List<NewsFeedComment> comments = newsfeedService.listNewsFeedComment(no);
			for(int i=0; i<comments.size(); i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("no", comments.get(i).getNo());
				map.put("id", comments.get(i).getWriter());
				User user = userService.findUser(new User(comments.get(i).getWriter()));
				map.put("nickname", user.getNickname());
				map.put("profile", imageService.profileFilenameToBlob(user.getProfile()).getBlob());
				map.put("comment", comments.get(i).getComment());
				map.put("regtime", comments.get(i).getRegtime());
				list.add(map);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);		
	}
	
	@ApiOperation(value = "뉴스피드 댓글 작성", notes = "입력 : userid, 게시글번호(feed_no), 내용(comment)")
	@PostMapping("/commentWrite")
	public ResponseEntity<?> writeComment(@RequestBody String js) throws Exception {
		// 뉴스피드 댓글 작성하기
		// 입력 : userid, comment
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			String userid = (String) jsonObj.get("userid");
			long feed_no = (long) jsonObj.get("feed_no");
			String comment = (String) jsonObj.get("comment");
			System.out.println("뉴스피드 댓글 작성 : "+userid+" "+comment);
			NewsFeedComment nfcomment = new NewsFeedComment();
			nfcomment.setWriter(userid);
			nfcomment.setFeed_no((int)feed_no);
			nfcomment.setComment(comment);
			newsfeedService.writeNewsFeedComment(nfcomment);
			NewsFeed newsfeed = newsfeedService.getNewsFeedByNo((int)feed_no);
			if(!newsfeed.getId().equals(userid)) {
				Notice notice = new Notice();
				notice.setId(newsfeed.getId());
				User user = userService.findUser(new User(userid));
				notice.setContent(user.getNickname()+"님이 게시글에 댓글을 달았습니다.");
				noticeService.writeNotice(notice);
			}
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "뉴스피드 댓글 수정", notes = "입력 : 댓글번호(no), 내용(comment)")
	@PatchMapping("/commentUpdate")
	public ResponseEntity<?> updateComment(@RequestBody String js) throws Exception {
		// 뉴스피드 댓글 수정하기
		// 입력 : 댓글번호, 수정내용
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			long no = (long) jsonObj.get("no");
			String comment = (String) jsonObj.get("comment");
			System.out.println("뉴스피드 댓글 수정 : "+no+" "+comment);
			NewsFeedComment nfcomment = new NewsFeedComment();
			nfcomment.setNo((int)no);
			nfcomment.setComment(comment);
			newsfeedService.modifyNewsFeedComment(nfcomment);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "뉴스피드 댓글 삭제", notes = "입력 : 댓글번호(no)")
	@DeleteMapping("/commentDelete")
	public ResponseEntity<?> deleteComment(@RequestParam int no) throws Exception {
		// 뉴스피드 댓글 삭제하기
		// 입력 : 댓글번호
		// 출력 : 성공, 실패
		try {
			System.out.println("뉴스피드 댓글 삭제 : "+no);
			newsfeedService.removeNewsFeedComment(no);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}
