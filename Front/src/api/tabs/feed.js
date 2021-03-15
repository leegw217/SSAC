import { createInstance } from "../index.js"

const instance = createInstance();
const url = "http://i4d102.p.ssafy.io/ssac"

function getArticle(userid, success, fail) {
  instance
    .get(`${url}/newsfeed/newsFeedList?userid=${userid}`)
    .then(success)
    .catch(fail);
}
 
function writeArticle(userid,content,filename,success, fail) {
  const config = {
    userid: userid,
    content: content,
    filename: filename
  }
  instance
    .post(`${url}/newsfeed/newsFeedWrite`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
 
function updateArticle(no, content, filename, success, fail) {
  const config = {
    no: no,
    content: content,
    filename: filename,
  }
  instance
    .patch(`${url}/newsfeed/newsFeedUpdate`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function deleteArticle(no, success, fail) {
  // const config = {
  //   no: no
  // }
  instance
    .delete(`${url}/newsfeed/newsFeedDelete?no=${no}`)
    .then(success)
    .catch(fail);
}
 
function likeArticle(userid, no, success, fail) {
  const config = {
    userid: userid,
    no: no
  }
  instance
    .post(`${url}/newsfeed/likeWrite`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function getLikeUsers(no, success, fail) {
  instance
    .get(`${url}/newsfeed/likeList?no=${no}`)
    .then(success)
    .catch(fail);
}
 
function unlikeArticle(userid, no, success, fail) {
  // const config = {
  //   userid: userid,
  //   no: no
  // }
  instance
    .delete(`${url}/newsfeed/likeDelete?userid=${userid}&no=${no}`)
    .then(success)
    .catch(fail);
}
 
function getComment(no, success, fail) {
  instance
    .get(`${url}/newsfeedcomment/commentList?no=${no}`)
    .then(success)
    .catch(fail);
}
 
function writeComment(userid,feed_no,comment, success, fail) {
  const config = {
    userid: userid,
    feed_no:feed_no,
    comment: comment,
  }
  instance
    .post(`${url}/newsfeedcomment/commentWrite`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function updateComment(no,comment, success, fail) {
  const config = {
    no: no,
    comment: comment,
  }
  instance
    .patch(`${url}/newsfeedcomment/commentUpdate`,JSON.stringify(config))
    .then(success)
    .catch(fail);
}
 
function deleteComment(no, success, fail) {
  instance
    .delete(`${url}/newsfeedcomment/commentDelete?no=${no}`)
    .then(success)
    .catch(fail);
}
 

 
// function uploadPicture(data, success, fail) {
//   instance.defaults.headers['Content-Type'] = 'multipart/form-data'

//   const body = {
//     userid: email,
//     userpw: password
//   };

// }

export { getArticle, writeArticle, updateArticle, deleteArticle, likeArticle, getLikeUsers, unlikeArticle, getComment, writeComment, updateComment, deleteComment};