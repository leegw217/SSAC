package com.ssac.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssac.image.service.ImageService;
import com.ssac.team.dto.Team;
import com.ssac.team.service.TeamService;
import com.ssac.user.dto.User;
import com.ssac.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private ImageService imageService;
	
	@ApiOperation(value = "친구, 팀 검색", notes = "입력 : 유저이메일(userid), 검색내용(search)")
	@GetMapping("/search")
	public ResponseEntity<?> getSearchResult(@RequestParam String userid, @RequestParam String search) throws Exception {
		try {
			List<User> temp = userService.getSearchUser(search);
			List<Map<String,Object>> users = new ArrayList<Map<String,Object>>();
			int size = temp.size()>5?5:temp.size();
			for(int i=0; i<size; i++) {
				Map<String, Object> user = new HashMap<String, Object>();
				user.put("id", temp.get(i).getId());
				user.put("nickname", temp.get(i).getNickname());
				user.put("profile", imageService.profileFilenameToBlob(temp.get(i).getProfile()).getBlob());
				users.add(user);
			}
			System.out.println("검색 userid : "+userid+", search : "+search);
			HashMap<String, String> hmap = new HashMap<String, String>();
			hmap.put("userid", userid);
			hmap.put("search", search);
			List<Team> teams = teamService.searchTeam(hmap);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("users", users);
			result.put("teams", teams);
			result.put("searched", true);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "팀추천", notes = "입력 : 유저이메일(userid), 설문결과(surveyAnswers)")
	@GetMapping("/recommend")
	public ResponseEntity<?> getRecommend(@RequestParam String userid, 
			@RequestParam String surveyAnswers) throws Exception {
		try {
			System.out.println("팀추천 userid : "+userid+", 설문 : "+surveyAnswers);
			Random random = new Random();
			random.setSeed(System.currentTimeMillis());
			int idx = 0;
			List<Team> newteam = teamService.listNewTeam(userid);
			List<Team> teams = new ArrayList<Team>();
			int event_no = 0;
			if(surveyAnswers.equals("축구")) event_no = 1;
			else if(surveyAnswers.equals("야구")) event_no = 2; 
			else event_no = 3;
			for(int i=0; i<newteam.size(); i++)
				if(newteam.get(i).getEvent_no() == event_no) teams.add(newteam.get(i));
			idx = random.nextInt(teams.size()-1);
			return new ResponseEntity<>(teams.get(idx), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "친구의 팀 조회", notes = "입력 : 유저이메일(userid)")
	@GetMapping("/friteams")
	public ResponseEntity<?> getFriendTeams(@RequestParam String userid) throws Exception {
		try {
			List<HashMap<String, Object>> teams = teamService.getFriendTeams(userid);
			List<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
			System.out.println("친구팀 조회 : "+userid);
			for(int i=0; i<teams.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				int no = (int) teams.get(i).get("team_no");
				String id = (String) teams.get(i).get("id");
				Team team = teamService.getTeam(no);
				User user = userService.findUser(new User(id));
				map.put("logo", team.getLogo());
				map.put("name", team.getName());
				map.put("no", no);
				map.put("friend_name", user.getNickname());
				map.put("people", (long) teams.get(i).get("count"));
				result.add(map);
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
}
