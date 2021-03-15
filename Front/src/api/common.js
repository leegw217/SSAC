import { createInstance } from "./index.js";

const instance = createInstance();
const url = "http://i4d102.p.ssafy.io/ssac"


function getAlert(userid, success, fail) {
  instance
    .get(`${url}/notice/noticeList?userid=${userid}`)
    .then(success)
    .catch(fail);
}

export { getAlert }