import { createInstance } from "../index.js";

const instance = createInstance();

const url = 'http://i4d102.p.ssafy.io:9000/ssac'
function addmyteam(userid, team_no, success, fail) {

  let config = {
    userid: userid,
    team_no: team_no
  }

  instance
    .post(`${url}//team/myTeamInsert`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}

export{ addmyteam, }  