package com.websocket.chat.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatRoom {
	private String roomId;
	private String name;

	public ChatRoom() {}
	
	public ChatRoom(String name) {
		this.roomId = UUID.randomUUID().toString();
		this.name = name;
	}
}
