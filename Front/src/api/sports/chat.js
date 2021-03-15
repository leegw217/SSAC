import { createInstance } from "../index.js"

const instance = createInstance();
//채팅방 생성 리스트
function createChat(userid, success, fail) {
  instance
    .get(`http://i4d102.p.ssafy.io/ssac/schedule/chatList?userid=${ userid }`)
    .then(success)
    .catch(fail);
}

//채팅방 목록 조회
function listChatroom(userid, success, fail) {
  instance
    .get(`http://i4d102.p.ssafy.io:9090/chat/roomList?userid=${ userid }`)
    .then(success)
    .catch(fail);
}

//채팅방 입장
function chatroomEnter(para, success, fail) {
  instance
    .get(`http://i4d102.p.ssafy.io:9090/chat/roomEnter?roomId=${para.roomid}&userid=${para.userid}`)
    .then(success)
    .catch(fail);
}

//채팅방 생성 하기
function registerChat(Chat, success, fail) {
  instance
    .post(`http://i4d102.p.ssafy.io:9090/chat/roomCreate`, JSON.stringify(Chat))
    .then(success)
    .catch(fail);
}


export {listChatroom, chatroomEnter, createChat, registerChat }