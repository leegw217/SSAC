package com.ssac.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssac.image.service.ImageService;
import com.ssac.team.dto.MyTeam;
import com.ssac.team.service.TeamService;
import com.ssac.user.dto.User;
import com.ssac.user.jwt.service.JwtService;
import com.ssac.user.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService service;
	@Autowired
	private ImageService imageService;
	@Autowired
	private TeamService teamService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody String js, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			User user = new User();
			user.setId((String) jsonObj.get("userid"));
			user.setPw((String) jsonObj.get("userpw"));
			System.out.println(user.getId());
			User check = service.findUser(user);
			if(check != null) {
				String token = jwtService.create(check);
				System.out.println("토큰생성 완료");
				resultMap.put("access-token", token);
				resultMap.put("userid", check.getId());
				resultMap.put("nickname", check.getNickname());
				resultMap.put("point", check.getPoint());
				resultMap.put("profile", imageService.profileFilenameToBlob(check.getProfile()).getBlob());
				resultMap.put("intro", check.getIntro());
				resultMap.put("grade", check.getGrade());
				List<MyTeam> myteam = teamService.listMyTeam(user.getId());
				List<Integer> myteams = new ArrayList<Integer>();
				for(int i=0; i<myteam.size(); i++) myteams.add(myteam.get(i).getTeam_no());
				resultMap.put("myteams", myteams);
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
}
