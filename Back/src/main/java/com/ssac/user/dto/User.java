package com.ssac.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String id;
	private String pw;
	private String nickname;
	private int point;
	private String profile;
	private String intro;
	private int grade;
	
	public User(String id) {
		this.id = id;
	}
	
	public User(String id, String pw, String nickname) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
	}
}
