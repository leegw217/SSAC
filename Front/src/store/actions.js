import jwt_decode from "jwt-decode";
import {  findByKakao, findByNaver } from "../api/user/login.js"
// findById,

export default {
  async GET_MEMBER_INFO({ commit }, data) {
    commit("setUserInfo",data)
    // let decode = jwt_decode(token);

    // await findById(
    //   decode.userid,
    //   (res) => {
    //     if (res.data.message === "success") {
    //       commit("setUserInfo", res.data.userInfo);
    //     } else {
    //       console.log("존재하지 않는 사용자 입니다.");
    //     }
    //   },
    //   (err) => {
    //     console.log(err);
    //   }
    // );
  },
  async GET_KAKAO_INFO({ commit }, token) {
    let decode = jwt_decode(token);

    await findByKakao(
      decode.userid,
      (res) => {
        if (res.data.message === "success") {
          commit("setUserInfo", res.data.userInfo);
        } else {
          console.log("존재하지 않는 사용자 입니다.");
        }
      },
      (err) => {
        console.log(err);
      }
    );
  },
  async GET_NAVER_INFO({ commit }, token) {
    
    let decode = jwt_decode(token);

    await findByNaver(
      decode.userid,
      (res) => {
        if (res.data.message === "success") {
          commit("setUserInfo", res.data.userInfo);
        } else {
          console.log("존재하지 않는 사용자 입니다.");
        }
      },
      (err) => {
        console.log(err);
      }
    );
  }, 
  async CREATE_USER1({ commit }, newuser) {
    commit('createUser1', newuser)
  },
  async CREATE_USER2({ commit }, myteams) {
    commit('createUser2', myteams)
  },
  saveQuestion: function ({ commit }, answerData) {
    // console.log('actions')
    commit('ADD_ANSWER', answerData)
  },
  saveMyTeam: function ({ commit }, selectTeam) {
    // console.log('actions')
    commit('ADD_MYTEAM', selectTeam)
  },
  saveChatroom: function ({ commit }, roomid) {
    // console.log('actions')
    commit('SAVE_CHATROOM', roomid)
  },
  makeProfile: function ({ commit }, data) {
    // console.log('actions')
    commit('MAKE_PROFILE', data)
  },
  nicknaming: function ({commit}, {selectedData,nickname}) {
    commit('NICKNAMING', {selectedData,nickname})
  },
  usernickname: function ({commit}, {nickname,introduce}) {
    commit('USERNICKNAME', {nickname,introduce})
  },
  footerOut: function ({commit}) {
    commit('FOOTEROUT')
  }
};
