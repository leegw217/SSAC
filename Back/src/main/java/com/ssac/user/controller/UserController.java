package com.ssac.user.controller;

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
import com.ssac.notice.dto.Notice;
import com.ssac.notice.service.NoticeService;
import com.ssac.schedule.service.ScheduleService;
import com.ssac.team.dto.MyTeam;
import com.ssac.team.service.TeamService;
import com.ssac.user.dto.User;
import com.ssac.user.jwt.service.JwtService;
import com.ssac.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Api("SSAC")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ScheduleService scheduleService;
	
	@ApiOperation(value = "회원가입", notes = "입력 : userid, userpw, usernickname, userteam")
	@PostMapping("/signup")
	public ResponseEntity<?> joinUser(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			User user = new User();
			user.setId((String) jsonObj.get("userid"));
			user.setPw((String) jsonObj.get("userpw"));
			user.setNickname((String) jsonObj.get("usernickname"));
			System.out.println("회원가입 : "+user.getId()+" "+user.getNickname());
			User check = userService.findUser(new User(user.getId()));
			if(check == null) {
				// 회원가입 먼저
				if(userService.createUser(user) > 0) {
					// 마이팀 추가
					List<Long> teams = (List<Long>) jsonObj.get("userteam");
					System.out.println("teams size " + teams.size());
					for(int i=0; i<teams.size(); i++) {
						MyTeam myteam = new MyTeam();
						myteam.setId(user.getId());
						myteam.setTeam_no(teams.get(i).intValue());
						myteam.setName(teamService.getTeam(teams.get(i).intValue()).getName());
						// 마이팀 추가가 성공하면
						if(teamService.writeMyTeam(myteam) > 0) {
							HashMap<String, Integer> map = new HashMap<String, Integer>();
							map.put("no", myteam.getTeam_no());
							map.put("count", 1);
							// 팔로우수 증가
							teamService.modifyTeamCount(map);
						}
					}
					return new ResponseEntity<>("success", HttpStatus.OK);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "유저 정보 조회", notes = "입력 : userid")
	@GetMapping("/userSelect")
	public ResponseEntity<?> selectUser(@RequestParam String userid) throws Exception {
		// 유저 정보 조회
		// 입력 : userid
		// 출력 : 유저 정보
		try {
			User user = userService.findUser(new User(userid));
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("userid", user.getId());
			resultMap.put("usernickname", user.getNickname());
			resultMap.put("point", user.getPoint());
			resultMap.put("profile", imageService.profileFilenameToBlob(user.getProfile()).getBlob());
			resultMap.put("intro", user.getIntro());
			resultMap.put("grade", user.getGrade());
			List<String> following = userService.getFollowingList(user.getId());
			List<String> follower = userService.getFollowerList(user.getId());
			resultMap.put("following", following);
			resultMap.put("follower", follower);
			List<MyTeam> myteams = teamService.listMyTeam(user.getId());
			List<Integer> myteam = new ArrayList<Integer>();
			for(int i=0; i<myteams.size(); i++) myteam.add(myteams.get(i).getTeam_no());
			resultMap.put("myteam", myteam);
			System.out.println("유저정보 조회 : "+userid);
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "유저 정보 수정", notes = "입력 : userid, 변경할 이미지이름(newimagename), 변경할 소개(newintro), 변경할닉네임(newnickname)")
	@PatchMapping("/userNickNameChange")
	public ResponseEntity<?> changeUserNickName(@RequestBody String js) throws Exception {
		// 유저 닉네임 수정
		// 입력 : userid, userpw, 변경할 닉네임
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			String userid = (String)jsonObj.get("userid");
			User check = new User();
			check.setId(userid);
			User user = userService.findUser(check);
			String newnickname = (String)jsonObj.get("newnickname");
			String newintro = (String)jsonObj.get("newintro");
			String newimagename = (String)jsonObj.get("newimagename");
			System.out.println("유저 닉네임 수정 : "+user.getId()+" "+newnickname+" "+newintro+" "+newimagename);
			user.setNickname(newnickname);
			user.setIntro(newintro);
			if(userService.modifyUserInformation(user) > 0)
				return new ResponseEntity<String>("success", HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "유저 비밀번호 수정", notes = "입력 : userid, 변경할 비밀번호(newpw)")
	@PatchMapping("/userPasswordChange")
	public ResponseEntity<?> changeUserPassword(@RequestBody String js) throws Exception {
		// 유저 비밀번호 수정
		// 입력 : userid, userpw, 변경할 비밀번호
		// 출력 : 성공, 실패
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			User check = new User();
			check.setId((String)jsonObj.get("userid"));
			User user = userService.findUser(check);
			System.out.println("유저 비밀번호 수정 : "+user.getId());
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getId());
			map.put("userpw", (String)jsonObj.get("newpw"));
			if(userService.modifyUserPassword(map) > 0)
				return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "유저 탈퇴", notes = "입력 : userid")
	@DeleteMapping("/userDelete")
	public ResponseEntity<?> deleteUser(@RequestParam String userid) throws Exception {
		// 유저 탈퇴
		// 입력 : userid, userpw
		// 출력 : 성공, 실패
		try {
			User check = new User();
			check.setId(userid);
			User user = userService.findUser(check);
			System.out.println("유저탈퇴 : "+user.getId());
			if(userService.removeUser(user) > 0)
				return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "팔로잉", notes = "입력 : userid, 팔로잉한 아이디(follow_id)")
	@PostMapping("/following")
	public ResponseEntity<?> following(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			HashMap<String, String> map = new HashMap<String, String>();
			String userid = (String)jsonObj.get("userid");
			String follow_id = (String)jsonObj.get("follow_id");
			map.put("userid", userid);
			map.put("follow_id", follow_id);
			System.out.println("팔로우 : "+map.get("userid")+" "+map.get("follow_id"));
			if(userService.doFollowing(map) > 0) {
				Notice notice = new Notice();
				notice.setId(follow_id);
				User user = userService.findUser(new User(userid));
				notice.setContent(user.getNickname()+"님이 회원님을 팔로우했습니다.");
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "이메일 중복여부 체크", notes = "입력 필요없음")
	@GetMapping("/isCheck")
	public ResponseEntity<?> ischeck() throws Exception {
		try {
			System.out.println("이메일 중복체크");
			List<String> users = userService.getAllUser();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.OK);
	}
	
	@ApiOperation(value = "구독 포인트 증가", notes = "입력 : 포인트 받을 사람 id (userid)")
	@GetMapping("/subscribe")
	public ResponseEntity<?> getSubscribe(@RequestParam String userid) throws Exception {
		try {
			System.out.println("구독 "+userid);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("point", 100);
			scheduleService.getPoint(map);
			return new ResponseEntity<>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
}
