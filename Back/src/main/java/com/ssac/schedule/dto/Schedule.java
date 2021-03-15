package com.ssac.schedule.dto;

import lombok.Data;

@Data
public class Schedule {
	private int id;
	private String start;
	private String start_time;
	private int team1_id;
	private int team2_id;
	private String name;
	private int team1_score;
	private int team2_score;
	private int events_no;
	private String betDone;
	private String gameDone;
	private String calDone;
}
