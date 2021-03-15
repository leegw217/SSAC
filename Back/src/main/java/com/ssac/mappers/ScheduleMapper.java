package com.ssac.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssac.schedule.dto.Bet;
import com.ssac.schedule.dto.Schedule;

@Mapper
public interface ScheduleMapper {
	public List<Schedule> selectSchedule() throws Exception;
	public List<String> selectToday(String time) throws Exception;
	public int insertBetting(Bet bet) throws Exception;
	public List<Bet> selectBetting(Integer schedule_id) throws Exception;
	public int updateBetDone(HashMap<String, Object> map) throws Exception;
	public int updateGameDone(HashMap<String, Object> map) throws Exception;
	public int updateCalDone(Integer event_id) throws Exception;
	public int updateTeam1Score(HashMap<String, Object> map) throws Exception;
	public int updateTeam2Score(HashMap<String, Object> map) throws Exception;
	public String selectBetByUserId(Bet bet) throws Exception;
	public int deleteBetting(Bet bet) throws Exception;
	public int updateUserPoint(HashMap<String, Object> map) throws Exception;
	public List<Schedule> selectTodaySchedule(String date) throws Exception;
}
