<template>

  <div>
    <Chat 
       :participants="participants"
       :myself="myself"
       :messages="messages"
       :onType="onType"
       :onMessageSubmit="onMessageSubmit"
       :chatTitle="chatTitle"
       :placeholder="placeholder"
       :colors="colors"
       :borderStyle="borderStyle"
       :hideCloseButton="hideCloseButton"
       :closeButtonIconSize="closeButtonIconSize"
       :submitIconSize="submitIconSize"
          @onImageClicked="onImageClicked"
          @onImageSelected="onImageSelected"
          @onMessageSubmit="send"
          @onType="onType"
          @onClose="onClose">
          </Chat>
  </div>

</template>


<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>

import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

import {Chat} from 'vue-quick-chat';
import 'vue-quick-chat/dist/vue-quick-chat.css';
import {chatroomEnter} from '@/api/sports/chat.js'

var sock = new SockJS("http://i4d102.p.ssafy.io:9090/ws-stomp");
var ws = Stomp.over(sock);

export default {
    name: 'ChatRoom',
    components: {
        Chat
    },
    data() {
        return {
            roomId: '',
            room: {},
            sender: '',
            message: '',
            // 메세지 담기
            messages: [],
            visible: true,
            participants: [],
            myself: {},
            messages: [],
            chatTitle: 'My chat title',
            placeholder: 'send your message',
            colors: {
                header: {
                    bg: '#6200EA',
                    text: '#fff'
                },
                message: {
                    myself: {
                        bg: '#fff',
                        text: '#616161'
                    },
                    others: {
                        bg: '#6200EA',
                        text: '#fff'
                    },
                    messagesDisplay: {
                        bg: '#f7f3f3'
                    }
                },
                submitIcon: '#6200EA',
                submitImageIcon: '#6200EA',
            },
            borderStyle: {
                topLeft: "10px",
                topRight: "10px",
                bottomLeft: "10px",
                bottomRight: "10px",
            },
            hideCloseButton: false,
            submitIconSize: 25,
            closeButtonIconSize: "20px",
            asyncMode: false,
            toLoad: [
                {
                    content: 'Hey, John Doe! How are you today?',
                    myself: false,
                    participantId: 2,
                    timestamp: {year: 2011, month: 3, day: 5, hour: 10, minute: 10, second: 3, millisecond: 123},
                    uploaded: true,
                    viewed: true,
                    type: 'text'
                },
                {
                    content: "Hey, Adam! I'm feeling really fine this evening.",
                    myself: true,
                    participantId: 3,
                    timestamp: {year: 2010, month: 0, day: 5, hour: 19, minute: 10, second: 3, millisecond: 123},
                    uploaded: true,
                    viewed: true,
                    type: 'text'
                },
            ],
            scrollBottom: {
                messageSent: true,
                messageReceived: false
            },
            displayHeader:true,
            timestampConfig: {   
                format: 'HH:mm',
                relative: false
            },
            // there are other options, you can check them here
            // https://soapbox.github.io/linkifyjs/docs/options.html
            linkOptions: {
                myself: {
                    className: 'myLinkClass',
                    events: {
                        click: function (e) {
                            alert('Link clicked!');
                        },
                        mouseover: function (e) {
                            alert('Link hovered!');
                        }
                    },
                    format: function (value, type) {
                        if (type === 'url' && value.length > 50) {
                            value = value.slice(0, 50) + '…';
                        }
                        return value;
                    }
                },
                others: {
                    className: 'othersLinkClass',
                    events: {
                        click: function (e) {
                            alert('Link clicked!');
                        },
                        mouseover: function (e) {
                            alert('Link hovered!');
                        }
                    },
                    format: function (value, type) {
                        if (type === 'url' && value.length > 50) {
                            value = value.slice(0, 50) + '…';
                        }
                        return value;
                    }
                }
            },
            // there are other options, you can check them here
            // https://soapbox.github.io/linkifyjs/docs/options.html
        }
    },
    created () {
        this.connect()

        let para = {
            roomid: this.$store.state.chat.roomid,
            userid: this.$store.state.user.userid
        };
        chatroomEnter(
            para,
            (response) => {
                console.log(response);
                this.participants= response.data.participants
                this.myself= response.data.myself
                this.messages= response.data.messages
                this.roomId = para.roomid
            },
            (error) => {
                console.log(error)
            });

    },
    methods: {
        connect() {
            const serverURL = 'http://i4d102.p.ssafy.io:9090/ws-stomp'
                let socket = new SockJS(serverURL);
                this.stompClient = Stomp.over(socket);
                console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
                this.stompClient.connect(
                    {},
                    frame => {
                    // 소켓 연결 성공
                    this.connected = true;
                    console.log('소켓 연결 성공', frame);
                    // 서버의 메시지 전송 endpoint를 구독합니다.
                    // 이런형태를 pub sub 구조라고 합니다.
                    this.stompClient.subscribe("/send", res => {
                        console.log('구독으로 받은 메시지 입니다.', res.body);
                        let now = new Date()
                        let data = JSON.parse(res.body)
                        this.message = {
                            content:data.message,
                            myself: this.$store.state.user.userid === data.sender,
                            participantId:data.sender,
                            timestamp:{
                                year:now.getFullYear(),
                                month:now.getMonth(),
                                day:now.getDate(),
                                hour:now.getHours(),
                                minute:now.getMinutes(),
                                second:now.getSeconds(),
                                millisecond:0
                            },
                            type:'text'
                        }
                        if(res.body.roomId === this.roomid){
                        console.log("방번호가 일치합니다.")
                        console.log(this.message)
                        // setTimeout(function () {
                        // var scrollContainer = document.getElementById('window__messages__container')
                        // var isScrolledToBottom = scrollContainer.scrollHeight - scrollContainer.clientHeight <= scrollContainer.scrollTop + 1
                        // if (!isScrolledToBottom) { scrollContainer.scrollTop = scrollContainer.scrollHeight }
                        // }, 201)
                        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
                        this.messages.push(this.message)
                        }
                    });
                    const msg = { 
                    roomId: this.$store.state.chat.roomid,
                    type: 'TALK',
                    sender: this.myself.id,
                    message: this.message,
                    username: this.myself.nickname
                    };
                    console.log(msg)
                    this.stompClient.send("/pub", JSON.stringify(msg), {});
                    // if (this.stompClient) {
                    //   this.stompClient.unsubscribe("/send")
                    //   this.stompClient.disconnect()
                    //   this.socket.close()
                    // }
                    },
                    error => {
                    // 소켓 연결 실패
                    console.log('소켓 연결 실패', error);
                    this.connected = false;
                    }

                );      
        },
        send(message) {
            this.message = message
            if (this.stompClient && this.stompClient.connected) {
                const msg = { 
                roomId: this.$store.state.chat.roomid,
                type: 'TALK',
                sender: this.myself.id,
                message: this.message.content,
                username: this.myself.name
                };
                console.log(msg)
                this.stompClient.send("/pub/receive", JSON.stringify(msg), {});
            }
            this.message = ''
    },  

        onType: function (event) {
            //here you can set any behavior
            console.log(event);
        },
        sendMessage: function() {
            ws.send("http://i4d102.p.ssafy.io:9090/pub/chat/message", {}, JSON.stringify({type:'TALK', roomId:this.$store.state.chat.roomid, sender:this.myself.id, message:this.message}));
            this.message = '';
        },
        recvMessage: function(recv) {
            console.log(1)
            console.log(recv)
            console.log(2)
            this.messages.unshift({"type":'text',"sender":recv.participantId,"content":recv.message,"myself":false,"year":recv.regtime.year,month:recv.regtime.month})
        },
        loadMoreMessages(resolve) {
            setTimeout(() => {
                resolve(this.toLoad); //We end the loading state and add the messages
                //Make sure the loaded messages are also added to our local messages copy or they will be lost
                this.messages.unshift(...this.toLoad);
                this.toLoad = [];
            }, 1000);
        },
        onMessageSubmit: function (message) {
            /*
            * example simulating an upload callback. 
            * It's important to notice that even when your message wasn't send 
            * yet to the server you have to add the message into the array
            */
            this.message = message;
            this.messages.push(message);
        
            /*
            * you can update message state after the server response
            */
            // timeout simulating the request
            setTimeout(() => {
                message.uploaded = true
            }, 2000)
        },
        onClose() {
            this.visible = false;
            this.$store.dispatch('footerOut');
            this.$router.push({name:'SportsChat'});
        },
        onImageSelected(files, message){
            let src = 'https://149364066.v2.pressablecdn.com/wp-content/uploads/2017/03/vue.jpg'
            this.messages.push(message);
            /**
             * This timeout simulates a requisition that uploads the image file to the server.
             * It's up to you implement the request and deal with the response in order to
             * update the message status and the message URL
             */
            setTimeout((res) => {
                message.uploaded = true
                message.src = res.src
            }, 3000, {src});
        },
        onImageClicked(message){
            /**
             * This is the callback function that is going to be executed when some image is clicked.
             * You can add your code here to do whatever you need with the image clicked. A common situation is to display the image clicked in full screen.
             */
            console.log('Image clicked', message.src)
        }
    }
}

</script>

<style>

.quick-chat-container {
    height: 100vh;
}
</style>