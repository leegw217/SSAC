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

import com.ssac.image.dto.Image;
import com.ssac.image.service.ImageService;
import com.ssac.newsfeed.dto.NewsFeed;
import com.ssac.newsfeed.dto.NewsFeedLike;
import com.ssac.newsfeed.service.NewsFeedService;
import com.ssac.notice.dto.Notice;
import com.ssac.notice.service.NoticeService;
import com.ssac.user.dto.User;
import com.ssac.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/newsfeed")
public class NewsfeedController {
	@Autowired
	private NewsFeedService newsfeedService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private UserService userService;
	@Autowired
	private NoticeService noticeService;
	
	@ApiOperation(value = "뉴스피드 탭에서 리스트 조회 (팔로우된 친구 피드 포함 10개)", notes = "입력 : userid")
	@GetMapping("/newsFeedList")
	public ResponseEntity<?> getAllNewsfeedList(@RequestParam String userid) throws Exception {
		// 뉴스피드 불러오기 (내가쓴글 + 내 팔로워가 쓴 글)
		// 입력 : userid
		// 출력 : 뉴스피드 게시글 (개수는 협의)
		try {
			System.out.println("뉴스피드 불러오기(전체) : "+userid);
			List<NewsFeed> newsfeeds = newsfeedService.listNewsFeed(userid);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0; i<newsfeeds.size(); i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("no", newsfeeds.get(i).getNo());
				map.put("id", newsfeeds.get(i).getId());
				User user = userService.findUser(new User(newsfeeds.get(i).getId()));
				map.put("nickname", user.getNickname());
				map.put("profile", imageService.profileFilenameToBlob(user.getProfile()).getBlob());
				map.put("content", newsfeeds.get(i).getContent());
				map.put("regtime", newsfeeds.get(i).getRegtime());
				Image image = imageService.feedFilenameToBlob(newsfeeds.get(i).getImagename());
				if(image != null) {
					map.put("imageBLOB", image.getBlob());
					map.put("imageName", image.getFilename());
				} else {
					map.put("imageBLOB", "");
					map.put("imageName", "");
				}
				List<NewsFeedLike> like = newsfeedService.listNewsFeedLike(newsfeeds.get(i).getNo());
				List<String> likeid = new ArrayList<String>();
				for(int j=0; j<like.size(); j++) likeid.add(like.get(j).getLike_id());
				map.put("like", likeid);
				map.put("comment", newsfeedService.countComment(newsfeeds.get(i).getNo()));
				list.add(map);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "프로필에서 본인이 작성한 피드 불러오기", notes = "입력 : userid")
	@GetMapping("/newsFeedInProfile")
	public ResponseEntity<?> getNewsfeedList(@RequestParam String userid) throws Exception {
		try {
			System.out.println("뉴스피드 불러오기(프로필) : "+userid);
			List<NewsFeed> newsfeeds = newsfeedService.listNewsFeedById(userid);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0; i<newsfeeds.size(); i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("no", newsfeeds.get(i).getNo());
				map.put("content", newsfeeds.get(i).getContent());
				map.put("regtime", newsfeeds.get(i).getRegtime());
				Image image = imageService.feedFilenameToBlob(newsfeeds.get(i).getImagename());
				if(image != null) map.put("image", image.getBlob());
				else map.put("image", "");
				List<NewsFeedLike> like = newsfeedService.listNewsFeedLike(newsfeeds.get(i).getNo());
				List<String> likeid = new ArrayList<String>();
				for(int j=0; j<like.size(); j++) likeid.add(like.get(j).getLike_id());
				map.put("like", likeid);
				map.put("comment", newsfeedService.countComment(newsfeeds.get(i).getNo()));
				list.add(map);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "뉴스피드 작성", notes = "입력 : userid, 내용(content), 파일이름(filename)")
	@PostMapping("/newsFeedWrite")
	public ResponseEntity<?> writeNewsfeed(@RequestBody String js) throws Exception {
		// 뉴스피드 작성하기
		// 입력 : userid, content
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			String userid = (String) jsonObj.get("userid");
			String content = (String) jsonObj.get("content");
			String filename = (String) jsonObj.get("filename");
			System.out.println("뉴스피드 작성 : "+userid+" "+filename);
			NewsFeed newsfeed = new NewsFeed();
			newsfeed.setId(userid);
			newsfeed.setContent(content);
			newsfeed.setImagename(filename);
			newsfeedService.writeNewsFeed(newsfeed);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "뉴스피드 수정", notes = "입력 : 게시글번호(no), 변경내용(content), 파일이름(filename)")
	@PatchMapping("/newsFeedUpdate")
	public ResponseEntity<?> updateNewsfeed(@RequestBody String js) throws Exception {
		// 뉴스피드 수정하기
		// 입력 : no, content
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			long no = (long) jsonObj.get("no");
			String content = (String) jsonObj.get("content");
			String filename = (String) jsonObj.get("filename");
			System.out.println("뉴스피드 수정 : "+no+" "+filename);
			NewsFeed newsfeed = new NewsFeed();
			newsfeed.setNo((int)no);
			newsfeed.setContent(content);
			newsfeed.setImagename(filename);
			newsfeedService.modifyNewsFeed(newsfeed);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "뉴스피드 삭제", notes = "입력 : 게시글번호(no)")
	@DeleteMapping("/newsFeedDelete")
	public ResponseEntity<?> deleteNewsfeed(@RequestParam int no) throws Exception {
		// 뉴스피드 삭제하기
		// 입력 : no
		// 출력 : 성공, 실패
		try {
			System.out.println("뉴스피드 삭제 : "+no);
			newsfeedService.removeNewsFeed(no);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	//////////////////// like ///////////////////////
	
	@ApiOperation(value = "뉴스피드 좋아요 누르기", notes = "입력 : userid, 게시글번호(no)")
	@PostMapping("/likeWrite")
	public ResponseEntity<?> writeLike(@RequestBody String js) throws Exception {
		// 좋아요 누르기
		// 입력 : 게시글 번호, userid
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			String userid = (String) jsonObj.get("userid");
			long no = (long) jsonObj.get("no");
			System.out.println("좋아요 : "+userid+" "+no);
			NewsFeedLike like = new NewsFeedLike();
			like.setLike_id(userid);
			like.setFeed_no((int)no);
			newsfeedService.writeNewsFeedLike(like);
			NewsFeed newsfeed = newsfeedService.getNewsFeedByNo((int)no);
			if(!newsfeed.getId().equals(userid)) {
				Notice notice = new Notice();
				notice.setId(newsfeed.getId());
				User user = userService.findUser(new User(userid));
				notice.setContent(user.getNickname()+"님이 게시글에 좋아요를 눌렀습니다.");
				noticeService.writeNotice(notice);
			}
			return new ResponseEntity<String>("success", HttpStatus.OK); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "뉴스피드 좋아요 취소", notes = "입력 : userid, 게시글번호(no)")
	@DeleteMapping("/likeDelete")
	public ResponseEntity<?> deleteLike(@RequestParam String userid, @RequestParam int no) throws Exception {
		// 좋아요 취소
		// 입력 : 게시글 번호, userid
		// 출력 : 성공, 실패
		try {
			System.out.println("좋아요 취소 : "+userid+" "+no);
			NewsFeedLike like = new NewsFeedLike();
			like.setLike_id(userid);
			like.setFeed_no(no);
			newsfeedService.removeNewsFeedLike(like);
			return new ResponseEntity<String>("success", HttpStatus.OK); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}
