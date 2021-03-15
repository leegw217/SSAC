import { createInstance } from "../index.js"

const instance = createInstance();

function listTeams(userid, success, fail) {
    instance
      .get(`http://i4d102.p.ssafy.io/ssac/team/newTeamList?userid=${userid}`)
      .then(success)
      .catch(fail);
  }
  
  // function addMyteam(team, success, fail) {
  //   instance
  //     .post("http://i4d102.p.ssafy.io:9000/ssac/profile/newteam", JSON.stringify(team))
  //     .then(success)
  //     .catch(fail);
  // }



  export { listTeams };