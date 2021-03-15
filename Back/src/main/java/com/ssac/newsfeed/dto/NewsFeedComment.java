package com.ssac.newsfeed.dto;

import lombok.Data;

@Data
public class NewsFeedComment {
	private int no;
	private int feed_no;
	private String writer;
	private String comment;
	private String regtime;
}
