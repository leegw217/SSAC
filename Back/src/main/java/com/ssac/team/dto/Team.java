package com.ssac.team.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Team {
	private int no;
	private int event_no;
	private String name;
	private byte[] logo;
	private int count;
}
