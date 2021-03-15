import { createInstance } from "../index.js";

const instance = createInstance();

function login(email, password, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  const body = {
    userid: email,
    userpw: password
  };

  instance
    .post("http://i4d102.p.ssafy.io/ssac/login/login", JSON.stringify(body))
    .then(success)
    .catch(fail);
}


function kakaologin(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );

  instance
    .post("/")
    .then(success)
    .catch(fail);
}


function naverlogin(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/")
    .then(success)
    .catch(fail);
}


async function findById(email, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .get(``)
    .then(success)
    .catch(fail);
}


async function findByKakao(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .get(``)
    .then(success)
    .catch(fail);
}


async function findByNaver(success, fail) {
  
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  
  await instance
    .get(``)
    .then(success)
    .catch(fail);
}

export { login, findById, kakaologin, naverlogin, findByKakao, findByNaver };
