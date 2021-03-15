package com.ssac.schedule.service;

import java.util.HashMap;
import java.util.List;

import com.ssac.schedule.dto.Bet;
import com.ssac.schedule.dto.Schedule;

public interface ScheduleService {

	List<Schedule> listSchedule() throws Exception;

	List<String> getTodayGame(String time) throws Exception;

	int writeBetting(Bet bet) throws Exception;

	List<Bet> getBettingInfo(Integer schedule_id) throws Exception;

	int betDone(HashMap<String, Object> map) throws Exception;

	int gameDone(HashMap<String, Object> map) throws Exception;

	int calDone(Integer schedule_id) throws Exception;

	int writeTeam1Score(HashMap<String, Object> map) throws Exception;

	int writeTeam2Score(HashMap<String, Object> map) throws Exception;

	String checkBetting(Bet bet) throws Exception;

	int cancleBetting(Bet bet) throws Exception;

	int getPoint(HashMap<String, Object> map) throws Exception;

	List<Schedule> getTodaySchedules(String date) throws Exception;

}