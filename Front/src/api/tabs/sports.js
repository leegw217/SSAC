import { createInstance } from "../index.js";

const instance = createInstance();

// http://i4d102.p.ssafy.io/ssac/schedule/schedule
const url = "http://i4d102.p.ssafy.io/ssac"
function getSchedule(success,fail) {
  instance
    .get("http://i4d102.p.ssafy.io/ssac/schedule/schedule")
    .then(success)
    .catch(fail);
}

function getTeam(success,fail) {
  instance
    .get("http://i4d102.p.ssafy.io/ssac/team/allTeamList")
    .then(success)
    .catch(fail);
}

function pmScore(event_id,n,pm,success,fail) {
  let config = {
    event_id : event_id,
    team_num : n,
    pm : pm
  }
  instance
    .patch(`${url}/schedule/score`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}

function betGame(event_id,user_id,n,success,fail) {
  let config = {
    schedule_id: event_id,
    userid: user_id,
    bet_num: n,
  }
  
  instance
    .post(`${url}/schedule/betting`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function endBet(event_id,bool,success,fail) {
let config = {
  event_id: event_id,
  bool: bool,
}

  instance
    .patch(`${url}/schedule/betDone`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function endGame(event_id,bool,success,fail) {
  let config = {
    event_id: event_id,
    bool: bool,
  }
  instance
    .patch(`${url}/schedule/gameDone`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function calculPts(event_no,users,pts,success,fail) {
  let config = {
    event_id: event_no,
    users: users,
    point: pts,
  }
  instance
    .patch(`${url}/schedule/calDone`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}

function getMyTeam(userid, success, fail) {
  instance
    .get(`${url}/team/myTeamList?userid=${userid}`)
    .then(success)
    .catch(fail);
}

function getTodayEventImage(success, fail) {
  instance
    .get(`${url}/schedule/todaySchedule`)
    .then(success)
    .catch(fail);
}
 
export { getTodayEventImage, getMyTeam, getSchedule, pmScore, betGame, endBet, endGame, calculPts, getTeam};