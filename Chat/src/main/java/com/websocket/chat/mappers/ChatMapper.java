package com.websocket.chat.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.websocket.chat.model.ChatMessage;
import com.websocket.chat.model.ChatRoom;
import com.websocket.chat.model.ChatUser;

@Mapper
public interface ChatMapper {
	public int insertChatRoom(ChatRoom chatroom) throws Exception;
	public int insertChatUser(HashMap<String, String> map) throws Exception;
	public List<ChatUser> selectChatUserByRoomId(String roomId) throws Exception;
	public int deleteChatUser(HashMap<String, String> map) throws Exception;
	public List<ChatRoom> selectChatRoom(String userId) throws Exception;
	public int insertChatMessage(ChatMessage chatmessage) throws Exception;
	public List<ChatMessage> selectChatMessage(String roomId) throws Exception;
	public ChatRoom selectChatRoomByRoomId(String roomId) throws Exception;
}
