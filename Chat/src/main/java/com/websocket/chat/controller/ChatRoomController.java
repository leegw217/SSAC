package com.websocket.chat.controller;

import com.websocket.chat.model.ChatMessage;
import com.websocket.chat.model.ChatRoom;
import com.websocket.chat.model.ChatUser;
import com.websocket.chat.service.ChatService;

import io.swagger.annotations.ApiOperation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/chat")
public class ChatRoomController {
	@Autowired
	private ChatService chatService;

	@ApiOperation(value = "신경안써도됨", notes = "입력 : userid")
	@GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        try {
        	System.out.println("roomInfo");
			return chatService.getChatRoomByRoomId(roomId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	@ApiOperation(value = "1. 채팅방 목록조회", notes = "입력 : userid")
    @GetMapping("/roomList")
	@ResponseBody
    public ResponseEntity<?> room(@RequestParam String userid) throws Exception {
        // 채팅방 목록
    	// 입력 : userid
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	List<ChatRoom> rooms = chatService.getChatRoom(userid);
    	for(int i=0; i<rooms.size(); i++) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("room", rooms.get(i));
    		List<ChatUser> users = chatService.getChatUser(rooms.get(i).getRoomId());
    		List<String> usernames = new ArrayList<String>();
    		for(int j=0; j<users.size(); j++) usernames.add(users.get(j).getUserName());
    		map.put("usernames", usernames);
    		list.add(map);
    	}
    	System.out.println("채팅방 목록 조회");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
	@ApiOperation(value = "2. 채팅방 생성(채팅할 사람 초대포함)", notes = "입력 : 채팅방이름(name), 채팅맴버 아이디 배열(ids), 채팅맴버 닉네임 배열(nicknames)")
    @PostMapping("/roomCreate")
	@ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody String js) throws Exception {
    	// 채팅방 만들기
    	// 입력 : 채팅방이름(name), 초대맴버리스트(ids)
    	JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			ChatRoom room = new ChatRoom((String) jsonObj.get("name"));
			if(chatService.createChatRoom(room) > 0) {
				System.out.println("채팅방 생성 성공");
				List<String> ids = (List<String>) jsonObj.get("ids");
				List<String> nicknames = (List<String>) jsonObj.get("nicknames");
				for(int i=0; i<ids.size(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("roomId", room.getRoomId());
					map.put("userId", ids.get(i));
					map.put("userName", nicknames.get(i));
					chatService.createChatUser(map);
				}
				return new ResponseEntity<>("success", HttpStatus.OK);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
    }
	
	@ApiOperation(value = "3. 채팅방입장(첫입장 이후 채팅내역, 참여인원 목록 불러오기)", notes = "입력 : userid, 채팅방번호(roomId)")
    @GetMapping("/roomEnter")
	@ResponseBody
    public ResponseEntity<?> enterChatRoom(@RequestParam String roomId, @RequestParam String userid) throws Exception {
    	// 채팅 내역 불러오기
    	// 입력 : 채팅방번호(roomId), userid
		
		List<ChatUser> users = chatService.getChatUser(roomId);
		List<ChatMessage> message = chatService.getChatMessage(roomId);
		Map<String, Object> myself = new HashMap<String, Object>();
		List<Map<String, Object>> participants = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();

		for(int i=0; i<users.size(); i++) {
			if(users.get(i).getUserId().equals(userid)) {
				myself.put("name", users.get(i).getUserName());
				myself.put("id", users.get(i).getUserId());
				myself.put("profilePicture", "https://placekitten.com/300/300");
			} else {
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("name", users.get(i).getUserName());
				p.put("id", users.get(i).getUserId());
				p.put("profilePicture", "https://placekitten.com/300/300");
				participants.add(p);
			}
		}
		
		for(int i=0; i<message.size(); i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("content", message.get(i).getMessage());
			if(message.get(i).getSender().equals(userid)) {
				m.put("myself", true);
				m.put("participantId", message.get(i).getSender());
			} else {
				m.put("myself", false);
				m.put("participantId", message.get(i).getSender());
			}
			Map<String, Object> times = new HashMap<String, Object>();
			times.put("year", Integer.parseInt(message.get(i).getRegtime().substring(0, 4)));
			times.put("month", Integer.parseInt(message.get(i).getRegtime().substring(5, 7)));
			times.put("day", Integer.parseInt(message.get(i).getRegtime().substring(8, 10)));
			times.put("hour", Integer.parseInt(message.get(i).getRegtime().substring(11, 13)));
			times.put("minute", Integer.parseInt(message.get(i).getRegtime().substring(14, 16)));
			times.put("second", Integer.parseInt(message.get(i).getRegtime().substring(17, 19)));
			times.put("millisecond", 0);
			m.put("timestamp", times);
			m.put("type", "text");
			messages.add(m);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("myself", myself);
		result.put("participants", participants);
		result.put("messages", messages);
		System.out.println("채팅방 입장 성공");
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    	
	@ApiOperation(value = "4. 채팅방 나가기", notes = "입력 : userid, 채팅방번호(roomId)")
	@DeleteMapping("/roomExit")
	@ResponseBody
	public ResponseEntity<?> exitChatRoom(@RequestBody String js) throws Exception {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(js);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("roomId", (String) jsonObj.get("roomId"));
			map.put("userId", (String) jsonObj.get("userid"));
			if(chatService.outChatRoom(map) > 0)
				return new ResponseEntity<>("success", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
	}
}
