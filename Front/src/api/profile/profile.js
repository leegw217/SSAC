import { createInstance } from "../index.js"

const instance = createInstance();
const url = "http://i4d102.p.ssafy.io/ssac"


//닉네임, 한줄소개 변경
function modifyNickname(para, success, fail) {
    instance
      .patch(`${url}/user/userNickNameChange`, JSON.stringify(para))
      .then(success)
      .catch(fail);
  }

//팔로잉
function doFollow(para, success, fail) {
    instance
      .patch(`${url}/user/following`, JSON.stringify(para))
      .then(success)
      .catch(fail);
  }

  

function deleteAccount(userid, success, fail) {
  instance
    .delete(`${url}/user/userDelete/${userid}`)
    .then(success)
    .catch(fail);
}
  
  export { modifyNickname, doFollow,deleteAccount };