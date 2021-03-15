package com.websocket.chat.controller;

import com.websocket.chat.model.ChatMessage;
import com.websocket.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
	@Autowired
	private ChatService chatService;

    @MessageMapping("/receive")
    
    @SendTo("/send")
    
    public ChatMessage SocketHandler(ChatMessage message) throws Exception {
        System.out.println("방번호 : "+message.getRoomId());
        System.out.println("보낸사람 : "+message.getSender());
        System.out.println("내용 : "+message.getMessage());
        chatService.chatting(message);
        
        return message;
    }
}
