package com.websocket.chat.service;

import java.util.HashMap;
import java.util.List;

import com.websocket.chat.model.ChatMessage;
import com.websocket.chat.model.ChatRoom;
import com.websocket.chat.model.ChatUser;

public interface ChatService {

	int createChatRoom(ChatRoom chatroom) throws Exception;

	int createChatUser(HashMap<String, String> map) throws Exception;

	List<ChatUser> getChatUser(String roomId) throws Exception;

	int outChatRoom(HashMap<String, String> map) throws Exception;

	List<ChatRoom> getChatRoom(String userId) throws Exception;

	int chatting(ChatMessage chatmessage) throws Exception;

	List<ChatMessage> getChatMessage(String roomId) throws Exception;

	ChatRoom getChatRoomByRoomId(String roomId) throws Exception;

}