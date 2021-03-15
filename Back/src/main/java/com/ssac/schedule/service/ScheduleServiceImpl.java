package com.ssac.schedule.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssac.mappers.ScheduleMapper;
import com.ssac.schedule.dto.Bet;
import com.ssac.schedule.dto.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleMapper mapper;
	
	@Override
	public List<Schedule> listSchedule() throws Exception {
		return mapper.selectSchedule();
	}
	
	@Override
	public List<String> getTodayGame(String time) throws Exception {
		return mapper.selectToday(time);
	}
	
	@Override
	public int writeBetting(Bet bet) throws Exception {
		return mapper.insertBetting(bet);
	}
	
	@Override
	public List<Bet> getBettingInfo(Integer schedule_id) throws Exception {
		return mapper.selectBetting(schedule_id);
	}
	
	@Override
	public int betDone(HashMap<String, Object> map) throws Exception {
		return mapper.updateBetDone(map);
	}
	
	@Override
	public int gameDone(HashMap<String, Object> map) throws Exception {
		return mapper.updateGameDone(map);
	}
	
	@Override
	public int calDone(Integer schedule_id) throws Exception {
		return mapper.updateCalDone(schedule_id);
	}
	
	@Override
	public int writeTeam1Score(HashMap<String, Object> map) throws Exception {
		return mapper.updateTeam1Score(map);
	}
	
	@Override
	public int writeTeam2Score(HashMap<String, Object> map) throws Exception {
		return mapper.updateTeam2Score(map);
	}
	
	@Override
	public String checkBetting(Bet bet) throws Exception {
		return mapper.selectBetByUserId(bet);
	}
	
	@Override
	public int cancleBetting(Bet bet) throws Exception {
		return mapper.deleteBetting(bet);
	}
	
	@Override
	public int getPoint(HashMap<String, Object> map) throws Exception {
		return mapper.updateUserPoint(map);
	}
	
	@Override
	public List<Schedule> getTodaySchedules(String date) throws Exception {
		return mapper.selectTodaySchedule(date);
	}
}
