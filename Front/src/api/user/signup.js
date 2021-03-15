import { createInstance } from "../index.js";

const instance = createInstance();

function signup(newuser, success, fail) {
  console.log(1)
  console.log(newuser)
  console.log(2)
  const body = {
    userid: newuser.email,
    userpw: newuser.password,
    usernickname: newuser.nickName,
    userteam: newuser.myteams
  };
  console.log(body)
  console.log(JSON.stringify(body))
  instance
    .post("http://i4d102.p.ssafy.io/ssac/user/signup", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function getUser(success, fail) {

  instance
    .get(`http://i4d102.p.ssafy.io/ssac/user/isCheck`)
    .then(success)
    .catch(fail);
}


// async function getteams(success, fail) {
//   instance.defaults.headers["access-token"] = window.localStorage.getItem(
//     "access-token"
//   );
//   await instance
//     .get(``)
//     .then(success)
//     .catch(fail);
// }


// async function findById(email, success, fail) {
//   instance.defaults.headers["access-token"] = window.localStorage.getItem(
//     "access-token"
//   );
//   await instance
//     .get(``)
//     .then(success)
//     .catch(fail);
// }

export { signup, getUser };
