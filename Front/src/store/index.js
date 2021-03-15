import Vue from 'vue'
import Vuex from 'vuex'
import getters from "./getters";
import actions from "./actions";
import mutations from "./mutations";
import socket from './modules/socket';

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production';

const state = {
  isLogin: false,
  isChatRoom: false,
  userInfo: null,
  isNav: false, 
  newUser: {
    email: "",
    password: "",
    nickName: "",
    myteams: []
  },
  surveyAnswers: [],
  chat: {
    roomid: ''
  },
  photos: {
    0: 'https://cdn.pixabay.com/photo/2014/10/14/20/24/the-ball-488716_1280.jpg',
    1: 'https://cdn.pixabay.com/photo/2016/04/24/21/29/football-1350720_1280.jpg',
    2: 'https://cdn.pixabay.com/photo/2016/08/02/15/47/baseball-field-1563858_1280.jpg',
    3: 'https://cdn.pixabay.com/photo/2018/10/20/14/57/ball-3760961_1280.jpg',
    4: 'https://t1.daumcdn.net/cfile/tistory/99EF21475A47381534',
    5: 'https://cgeimage.commutil.kr/phpwas/restmb_allidxmake.php?idx=999&simg=2020072711565608529283f60ce7c21850179202.jpg',
    6: 'https://post-phinf.pstatic.net/MjAxOTExMjVfMTQ5/MDAxNTc0NjQ1MjI1MjY2.Zn4qn_krvEvxdzNLmtsHXm6r28u-MTxqZ0s_5VaJQesg.magZqCx3QZWeAXl9p6CldnXvib7ovGAjFPXg2BU-xTsg.JPEG/394462_292576_570.jpg?type=w1200',
    7: 'https://lh3.googleusercontent.com/proxy/yL8EcYIPeBF8kImj8okhIFkWVuHv82jEnukkqfV3ses6WajszvyMPhXwCaLiNF4NtB6hGTF6DLB0xnZ4dmGy390DEFdFcaVuP97GStshESVMA-Cy0cv8RUc',
    8: 'https://t1.daumcdn.net/cfile/tistory/99A9433B5A3E30D92A',
    9: 'https://cdn.theathletic.com/app/uploads/2020/04/16183331/GettyImages-1187112662.jpg'
  },
  user: {
    userid: "",
    email: "",
    password: "",
    nickname: "",
    myteams: [],
    img: "https://placekitten.com/300/300",
    intro: "",
    profile: "",
  },
  myTeams: [
    {name: "T1", img: "https://placekitten.com/300/300" , nickname: "", count: 14565, event_no: 3},
    {name: "대구FC", img: "https://placekitten.com/300/300" , nickname: "", count: 1234, event_no: 1},
    {name: "삼성라이온즈", img: "https://placekitten.com/300/300" , nickname: "", count: 76567, event_no: 2},
  ],
  teams: [
    {name: "T1", img: "https://placekitten.com/300/300" , nickname: "", count: 14565, event_no: 3},
    {name: "GEN.G", img: "https://placekitten.com/300/300" , nickname: "", count: 14565, event_no: 3},
    {name: "SSG 일렉트로스", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 2},
    {name: "롯데 자이언츠", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 2},
    {name: "FC서울", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 1},
    {name: "수원삼성 블루윙즈", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 1},
    {name: "DRX", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 3},
    {name: "리브샌박", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 3},
    {name: "전북현대", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 1},
    {name: "LG 트윈스", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 2},
    {name: "두산 베어스", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 2},
    {name: "한화생명", img: "https://placekitten.com/300/300", nickname: "", count: 14565, event_no: 3},
    {name: "대구FC", img: "https://placekitten.com/300/300", nickname: "", count: 1234, event_no: 1},
    {name: "삼성라이온즈", img: "https://placekitten.com/300/300", nickname: "", count: 76567, event_no: 2},
  ],
  articles: [
    {title: "오늘 경기 완전 노잼이었음", context: "나는 저돈 반만 받아도 되니까 내가 뛸게 걍"},
    {title: "지금은 새벽 1시", context: "아직은 할만하다"},
    {title: "근데 완전 눈이 감길라카는건 어쩌지", context: "안된다 오늘은 안잔다는 마인드니까"},
    {title: "침착맨은 한줄기 희망이다", context: "내가 잠오는걸 강제로 막아준다"},
    {title: "이대호 연봉 10억 넘네", context: "진짜 부럽다 나도 연봉 10억 찍어보고싶다."},
    {title: "데이식스 노래도 진짜 좋다", context: "들어도 들어도 안질리고 신난ㄴ다"},
    {title: "근데 여기서 의문인점", context: "나는 여기서 왜 일기를 쓰고 있는가"},
    {title: "왜긴 왜야", context: "더미를 채워야하기 때문이지"},
    {title: "마지막 할말은", context: "우리조,,,, 그냥,,, 화이팅,,,!!!"},
  ],
  test:[]
};

export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters,
  modules: {
    socket,
  },
  strict: debug,
  // plugins: debug ? [createLogger()] : []
})
