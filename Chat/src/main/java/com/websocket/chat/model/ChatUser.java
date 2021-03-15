package com.websocket.chat.model;

import lombok.Data;

@Data
public class ChatUser {
	private int no;
	private String roomId;
	private String userId;
	private String userName;
}
