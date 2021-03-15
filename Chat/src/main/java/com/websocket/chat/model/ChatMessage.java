package com.websocket.chat.model;

import lombok.Data;

@Data
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람 아이디
    private String username; // 메시지 보낸사람 이름
    private String message; // 메시지
    private String regtime; // 작성시간
}
