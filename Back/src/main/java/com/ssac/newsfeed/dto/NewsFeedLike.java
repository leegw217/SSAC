package com.ssac.newsfeed.dto;

import lombok.Data;

@Data
public class NewsFeedLike {
	private int no;
	private int feed_no;
	private String like_id;
	private String regtime;
}
