export default {
  setIsLogined(state, isLogin) {
    state.isLogin = isLogin;
  },
  setUserInfo(state, userInfo) {
    state.isLogin = true;
    console.log(state.isLogin)
    state.user = userInfo;
    console.log(userInfo)
  },
  logout(state) {
    state.isLogin = false;
    state.userInfo = null;
  },
  createUser1(state, newuser) {
    state.newUser.email = newuser.email
    state.newUser.password = newuser.password
    state.newUser.nickName = newuser.nickName
  },
  createUser2(state, myteams) {
    state.newUser.myteams = myteams
  },
  ADD_ANSWER: function (state, answerData) {
    // console.log('mutations')
    // console.log(answerData)
    state.surveyAnswers.push(answerData)
  },
  ADD_MYTEAM: function (state, selectTeam) {
    // console.log('mutations')
    console.log(selectTeam)
    state.myTeams.push(selectTeam.no)
  },
  SAVE_CHATROOM: function (state, roomid) {
    // console.log('mutations')
    console.log(roomid)
    state.chat.roomid = roomid
  },
  MAKE_PROFILE: function (state, data) {
    // console.log('mutations')
    
    state.profile = data
  },
  NICKNAMING: function (state, {selectedData,nickname}) {
    state.myTeams = state.myTeams.map((team) => {
      if (team.name === selectedData.name) {
        return {
          ...team,
          nickname: nickname
        }
      }
      return team
    })
  },
  USERNICKNAME: function (state, {nickname,introduce})  {
    state.user.nickname = nickname,
    state.user.introduce = introduce  
  },
  FOOTEROUT: function (state)  {
      state.isChatRoom = !state.isChatRoom
    },
  GUDOK: function(state) {
    state.user.point += 100
  }
};
