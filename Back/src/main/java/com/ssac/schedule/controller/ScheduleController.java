package com.ssac.schedule.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssac.image.service.ImageService;
import com.ssac.schedule.dto.Bet;
import com.ssac.schedule.dto.Schedule;
import com.ssac.schedule.service.ScheduleService;
import com.ssac.team.service.TeamService;
import com.ssac.user.dto.User;
import com.ssac.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/schedule")
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;
	
	@ApiOperation(value = "전체 경기일정 조회", notes = "입력 없어도됨")
	@GetMapping("/schedule")
	public ResponseEntity<?> getScheduleList() throws Exception {
		// 경기 일정 조회
		// 입력 : userid
		// 출력 : 경기 일정
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			List<Schedule> list = scheduleService.listSchedule();
			for(int i=0; i<list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				List<Bet> betting = scheduleService.getBettingInfo(list.get(i).getId());
				List<String> team1_user = new ArrayList<String>();
				List<String> team2_user = new ArrayList<String>();
				List<String> draw_user = new ArrayList<String>();
				for(int j=0; j<betting.size(); j++) {
					if(betting.get(j).getBet_num() == 1) team1_user.add(betting.get(j).getUserid());
					else if(betting.get(j).getBet_num() == 2) team2_user.add(betting.get(j).getUserid());
					else draw_user.add(betting.get(j).getUserid());
				}
				map.put("id", list.get(i).getId());
				map.put("start", list.get(i).getStart());
				map.put("start_time", list.get(i).getStart_time());
				map.put("team1_id", list.get(i).getTeam1_id());
				map.put("team2_id", list.get(i).getTeam2_id());
				map.put("name", list.get(i).getName());
				map.put("team1_score", list.get(i).getTeam1_score());
				map.put("team2_score", list.get(i).getTeam2_score());
				map.put("events_no", list.get(i).getEvents_no());
				map.put("team1_user", team1_user);
				map.put("team2_user", team2_user);
				map.put("draw_user", draw_user);
				map.put("betDone", list.get(i).getBetDone());
				map.put("gameDone", list.get(i).getGameDone());
				map.put("calDone", list.get(i).getCalDone());
				result.add(map);
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "당일 경기일정 조회", notes = "입력 없어도됨")
	@GetMapping("/todaySchedule")
	public ResponseEntity<?> getTodaySchedule() throws Exception {
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date time = new Date();
			String today = format.format(time);
			List<Schedule> list = scheduleService.getTodaySchedules(today);
			for(int i=0; i<list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", list.get(i).getId());
				map.put("start", list.get(i).getStart());
				map.put("start_time", list.get(i).getStart_time());
				map.put("team1_id", list.get(i).getTeam1_id());
				map.put("team2_id", list.get(i).getTeam2_id());
				map.put("name", list.get(i).getName());
				map.put("team1_score", list.get(i).getTeam1_score());
				map.put("team2_score", list.get(i).getTeam2_score());
				map.put("events_no", list.get(i).getEvents_no());
				map.put("team1_logo", teamService.getTeam(list.get(i).getTeam1_id()).getLogo());
				map.put("team2_logo", teamService.getTeam(list.get(i).getTeam2_id()).getLogo());
				map.put("betDone", list.get(i).getBetDone());
				map.put("gameDone", list.get(i).getGameDone());
				map.put("calDone", list.get(i).getCalDone());
				result.add(map);
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "당일 경기 제목, 팔로우 목록 불러오기", notes = "입력 : userid")
	@GetMapping("/chatList")
	public ResponseEntity<?> getTeamList(@RequestParam String userid) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date time = new Date();
			String today = format.format(time);
			List<String> todayGame = scheduleService.getTodayGame(today);
			List<String> following = userService.getFollowingList(userid);
			List<User> follow = new ArrayList<User>();
			System.out.println(today);
			for(int i=0; i<following.size(); i++) {
				User friend = userService.findUser(new User(following.get(i)));
				follow.add(friend);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("game", todayGame);
			map.put("follow", follow);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "배팅하기", notes = "입력 : userid, 경기번호(schedule_id), 배팅정보(bet_num)-1:1팀, 2:2팀, 3:무승부")
	@PostMapping("/betting")
	public ResponseEntity<?> doBetting(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			Bet bet = new Bet();
			long s = (long) jsonObj.get("schedule_id");
			long n = (long) jsonObj.get("bet_num");
			String userid = (String) jsonObj.get("userid");
			bet.setSchedule_id((int) s);
			bet.setUserid(userid);
			bet.setBet_num((int) n);
			System.out.println(s+" "+n+" "+userid);
			int result = 0;
			String check = scheduleService.checkBetting(bet);
			if(check != null) result = scheduleService.cancleBetting(bet);
			else result = scheduleService.writeBetting(bet);
			
			if(result > 0) {
				System.out.println("배팅완료");
				return new ResponseEntity<>("success", HttpStatus.OK);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "점수 조작", notes = "입력 : 경기번호(event_id), 팀 번호(team_num), 점수증가/감소(pm)")
	@PatchMapping("/score")
	public ResponseEntity<?> score(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			long event_id = (long) jsonObj.get("event_id");
			long team_num = (long) jsonObj.get("team_num");
			long pm = (long) jsonObj.get("pm");
			System.out.println(event_id+" "+team_num+" "+pm);
			int e = (int) event_id;
			int t = (int) team_num;
			int p = (int) pm;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("event_id", e);
			map.put("pm", p);
			int result = 0;
			if(t == 1) result = scheduleService.writeTeam1Score(map);
			else result = scheduleService.writeTeam2Score(map);
			if(result > 0) return new ResponseEntity<>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "배팅종료", notes = "입력 : 경기번호(event_id), 종료여부(bool : true/false - string으로)")
	@PatchMapping("/betDone")
	public ResponseEntity<?> bettingDone(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			HashMap<String, Object> map = new HashMap<String, Object>();
			long event_id = (long) jsonObj.get("event_id");
			map.put("event_id", (int) event_id);
			map.put("bool", (String) jsonObj.get("bool"));
			if(scheduleService.betDone(map) > 0) return new ResponseEntity<>("success", HttpStatus.OK);			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게임종료", notes = "입력 : 경기번호(event_id), 종료여부(bool : true/false - string으로)")
	@PatchMapping("/gameDone")
	public ResponseEntity<?> gamingDone(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			HashMap<String, Object> map = new HashMap<String, Object>();
			long event_id = (long) jsonObj.get("event_id");
			map.put("event_id", (int) event_id);
			map.put("bool", (String) jsonObj.get("bool"));
			if(scheduleService.gameDone(map) > 0) return new ResponseEntity<>("success", HttpStatus.OK);			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "정산종료", notes = "입력 : 경기번호(event_id), 아이디 배열(users), 포인트(point)")
	@PatchMapping("/calDone")
	public ResponseEntity<?> calculateDone(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			long e = (long) jsonObj.get("event_id");
			int event_id = (int) e;
			if(scheduleService.calDone(event_id) > 0) {
				List<String> users = (List<String>) jsonObj.get("users");
				long p = (long) jsonObj.get("point");
				int point = (int) p;
				System.out.println(e +" "+ p);
				System.out.println(users.size());
				// 포인트 정산하기
				for(int i=0; i<users.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("userid", users.get(i));
					map.put("point", point);
					scheduleService.getPoint(map);
				}
				return new ResponseEntity<>("success", HttpStatus.OK);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
}
