<template>
  <div style="width:100%;">
    <!-- Systembar & Navbar -->
    <div style="position:fixed; z-index:100; width:100%; top:0px;">
      <v-system-bar color="deep-purple darken-3"></v-system-bar>
      <v-app-bar
        color="deep-purple accent-4"
        dark
        height="54px"
      >
        <v-row>
          <v-col cols="3">
          </v-col>
          <v-col class="mt-1" cols="6">
            <h2 align="center" justify="center">Feed</h2>
          </v-col>
          <v-col cols="3">
          </v-col>
        </v-row>

      </v-app-bar>
    </div>
     <br>
     <br>
     <br>
    <!-- 새 글 작성 -->
    <v-card 
      ref="form"
      max-width="350"
      class=" mt-5 ml-3 mr-3 mb-5"
    >
      <v-list-item two-line class="grow">
        <v-list-item-avatar color="grey" size="55">
          <v-img
            class="elevation-6"
            alt=""
            :src="changeBlob(user.profile)"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content class="ml-1" justify="center">
          <v-list-item-title class="headline mb-2">{{ user.nickname }}</v-list-item-title>
        </v-list-item-content>
          
        <!-- 사진 업로드 -->
        <v-btn
          icon
          @click.stop="dialog = true"
        >
          <v-icon size="24px">
            mdi-image
          </v-icon>
        </v-btn>          
        <v-dialog v-model="dialog" width="500">
          <v-card>
            <v-file-input 
              class="d-flex align-center justify-center pa-4 mx-auto" 
              label="File input" 
              filled
              prepend-icon="mdi-camera"
              accept="image/*" 
              chips
              v-model="files"
            ></v-file-input>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  text
                  @click="dialog = false"
                >취소</v-btn>
              <v-btn color="primary" text @click="upload">
                적용
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-list-item>
        
      <!-- 게시글 업로드 -->
      <v-textarea
        v-model="newText"
        color="teal"
        class="ml-3 mr-3"
      >
        <template v-slot:label>
          <div>
            {{ user.nickname }}님, 무슨 생각을 하고 계신가요?
          </div>
        </template>
      </v-textarea>

      <div class="pb-5" align="center" justify="center">
        <v-btn
          class="white--text"
          color="#8187ff"
          @click="createNewFeed"
          width="265"
        >
          Submit
        </v-btn>
      </div>          
    </v-card>

    <!-- 피드 -->
    <div v-for="(feed, idx) in feeds" :key="idx">
      <v-card
        v-bind="attrs"
        class="ml-3 mr-3 mb-5"
        light
      >
        <v-list-item two-line class="grow">
            <v-list-item-avatar color="grey" size="55">
              <v-img
                class="elevation-6"
                alt=""
                :src="changeBlob(feed.profile)"
              ></v-img>
            </v-list-item-avatar>

            <v-list-item-content class="ml-1" justify="center">
              <v-list-item-title class="headline mb-2">{{ feed.nickname }}</v-list-item-title>
              <v-list-item-subtitle>{{ timeForToday(feed.regtime) }}</v-list-item-subtitle>
              <!-- <v-list-item-subtitle>{{ feed.regtime }}</v-list-item-subtitle> -->
            </v-list-item-content>
            <v-icon 
              v-if="feed.id === user.userid"
              @click="editModal(feed)"
              justify="end"
              align="end"
            >
              mdi-pencil
            </v-icon>
        </v-list-item>

        <v-img
          v-if="feed.image"
          :src="feed.image"
          max-height="200"
          max-width="100%"
        ></v-img>

        <v-card-text class="text--primary">
          <h3 class="darken-grey--text">{{ feed.content }}</h3>
        </v-card-text>

        <v-card-actions>
          <v-list-item class="grow">
            <v-row
              align="center"
              justify="end"
            >
              <v-icon class="mr-1" @click="likeFeed(feed)" :color="isLike(feed.like)">
                mdi-heart
              </v-icon>
              <span class="subheading mr-2">{{ feed.like.length }}</span>
              <span class="mr-1">·</span>
              <v-btn
                icon
                @click="changeModal(feed)"
              >
                <v-icon class="mr-1">                  
                  mdi-message-text
                </v-icon>
              </v-btn>
              {{feed.comment}}
            </v-row>
          </v-list-item>
        </v-card-actions>    
      </v-card>
    </div>

    <br>
    <br>
    <br>
    <br>
    <v-row justify="center">
      <v-dialog
        v-model="modal"
        fullscreen
        hide-overlay
        transition="dialog-bottom-transition"
      >
        <v-card>
          <v-toolbar
            dark
            color="primary"
          >
            <!-- <v-btn
              icon
              dark
              @click="modal = false"
            >
              <v-icon>mdi-close</v-icon>
            </v-btn> -->
            <v-toolbar-title>댓글</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                dark
                text
                @click="modal = false"
              >
                닫기
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>

          <v-card
            elevation="5"
            max-width="444"
            class="mx-auto"
          >
            <v-list two-line>
              <v-list-item>
                <v-list-item-avatar>
                  <v-img :src="changeBlob(selected.profile)"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>{{selected.nickname}}</v-list-item-title>
                  <v-list-item-subtitle>{{selected.id}}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-img
              :src="selected.image"
            >
            </v-img>
            <v-card-title>
              {{selected.content}}
            </v-card-title>
          </v-card>

          <v-divider></v-divider>
              <v-timeline
                align-top
                dense
              >
                <v-timeline-item
                  v-for="(comment, idx) in comments"
                  :key="idx"
                  small
                >
                  <template v-slot:icon>
                    <v-avatar>
                      <img :src="changeBlob(comment.profile)">
                    </v-avatar>
                  </template>
                  <v-card class="elevation-1">
                    <v-card-text><strong>{{ comment.nickname }}</strong> @{{ timeForToday(comment.regtime) }}</v-card-text>
                    <v-card-text class="font-weight-normal" v-show="!editCommBool[idx]">
                      {{ comment.comment }} 
                        <v-icon
                          v-if="comment.id === user.userid"
                          @click="editCommentBool(comment.content,idx)"
                        >
                          <!-- @click="editModal(feed)" -->
                          mdi-pencil
                        </v-icon>
                        <v-icon
                          v-if="comment.id === user.userid"
                          @click="delComment(comment.no,idx)"
                        >
                          mdi-delete
                        </v-icon>
                    </v-card-text>
                    <div v-show="editCommBool[idx]">
                      <v-text-field
                        label="new Comments"
                        solo
                        v-model="editComm"
                      ></v-text-field>
                      <v-btn @click="editCommentBool('',idx)">
                        취소
                      </v-btn>
                      <v-btn @click="editComment(comment,idx)">
                        수정
                      </v-btn>
                    </div>
                  </v-card>
                </v-timeline-item>
              </v-timeline>
                <v-row>
                  <v-col cols=2 class="ml-7">
                    <v-list-item-avatar>
                      <v-img 
                        :src="changeBlob(user.profile)"
                        ></v-img>
                    </v-list-item-avatar>
                  </v-col>
                  <v-col cols=6 class="px-0">
                    <v-text-field
                      label="new Comments"
                      solo
                      v-model="newComment"
                    ></v-text-field>
                  </v-col>
                  <v-col cols=3>
                    <v-btn @click="addNewComment(selected.no,selidx)">
                      입력
                    </v-btn>
                  </v-col>
                </v-row>
        </v-card>
      </v-dialog>
    </v-row>

    <v-row justify="center">
      <v-dialog
        v-model="editmodal"
        fullscreen
        hide-overlay
        transition="dialog-top-transition"
      >
        <v-card>
          <v-toolbar
            dark
            color="primary"
          >
            <!-- <v-btn
              icon
              dark
              @click="modal = false"
            >
              <v-icon>mdi-close</v-icon>
            </v-btn> -->
            <v-toolbar-title>글 수정</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                dark
                text
                @click="editmodal = false"
              >
                취소
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-divider></v-divider>
          <v-container fluid>
            <v-textarea
              clearable
              clear-icon="mdi-close-circle"
              label="Content"
              v-model="selected.content"
            ></v-textarea>
            <v-img
              :src="selected.image"
            >
            </v-img>
            <v-file-input 
              class="d-flex align-center justify-center pa-4 mx-auto" 
              label="File input" 
              filled
              prepend-icon="mdi-camera"
              accept="image/*" 
              chips
              v-model="files"
            ></v-file-input>
            ※글 수정에서 사진 업로드시<br>
            기존 사진에서 변경됩니다. <br>
            <v-btn color="primary" @click="upload2">
              적용
            </v-btn>
            <v-divider></v-divider>
            <br>
            <v-btn @click="deleteFeed(selected)">
              삭제하기
            </v-btn>
            <v-btn @click="updateFeed(selected)">
              수정하기
            </v-btn>
          </v-container>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
  
</template>

<script>
import { mapState } from 'vuex';
import { getArticle, writeArticle, updateArticle, deleteArticle, likeArticle, getLikeUsers, unlikeArticle,  getComment, writeComment, updateComment, deleteComment} from '@/api/tabs/feed.js'
import axios from "axios" 

export default {
  name:"Feed",
  data () {
    return {
      feeds: [
        {
        },
      ],
      attrs: {
        boilerplate: true,
        elevation: 2,
      },
      newText : "",
      newImg: "",
      newImgsrc: "",
      newImgBlob: "",
      newComment : "",
      editComm : "",
      images: false,
      editCommBool : [false,],
      dialog:false,
      modal:false,
      editmodal:false,
      selidx:Number,
      selected:{
        no:Number,
        id:String,
        nickname:String,
        profile:"",
        regtime:String,
        like:[],
        content:String,
        image:{
          image:'',
          filename:'',
        },
        comment:Number
      },
      comments: [],
      editFeed:{
        no:Number,
        id:String,
        nickname:String,
        profile:"",
        regtime:String,
        like:[],
        content:String,
        image:{
          image:'',
          filename:'',
        },
        comment:Number
      },
      files:[],
    }
  },
  computed: {
    ...mapState([
      'user'
    ])
  },
  mounted() {
    // console.log(this.user.userid)
    getArticle(
      this.user.userid,
      (res) => {
        console.log(res.data)
        this.feeds = res.data
        for (let idx = 0; idx<this.feeds.length; idx++) {
          this.feeds[idx].image = this.changeBlob(this.feeds[idx].imageBLOB)
        }
      },
      (err) => {
        console.log(err)
      }
    ),
    this.images = true
  },
  methods: {
    changeShow(idx) {
      this.$set(this.show, idx, !this.show[idx])
    },
    addNewComment(no,selidx) {
      let comm = this.newComment
      if (comm.length === 0){
        alert("아무것도 입력하지 않았습니당")
      } else {
        writeComment(
          this.user.userid,no,comm,
          (res) => {
            console.log(res.data)
            this.comments.push({
              "regtime": new Date(),
              "no":no,
              "nickname":this.user.nickname,
              "id":this.user.userid,
              "comment":comm,
              "profile":this.user.profile,
            }
            )
            this.feeds[selidx].comment += 1
          },
          (err) => {
            console.log(err)
            console.log(2)
          }
        )
        this.newComment = ''
      }
    },
    editCommentBool(content,idx){
      this.editCommBool[idx] = !this.editCommBool[idx]
      
      this.editComm = content
    },
    editComment(comm,idx) {
      let no = comm.no
      let comment = this.editComm
      if (comment.length>0){
        updateComment(
          no,comment,
          (res) => {
            console.log(res.data)
            comm.comment = comment
            this.editCommBool[idx] = false
          },
          (err) => {
            console.log(err)
          }
        )
      } else {
        alert("빈 댓글은 허용되지 않습니다.")
      }
    },
    delComment(no,idx){
      deleteComment(
        no,
        (res) => {
          console.log(res.data)
          this.comments.splice(idx,1)
        },
        (err) => {
          console.log(3)
          console.log(err)
        }
      )
    },
    createNewFeed() {
      writeArticle(
        this.user.userid,this.newText,this.newImg,
        (res) => {
          console.log(res.data)
          let newArticle = 
          {
            no:-1,
            id:this.user.userid,
            nickname:this.user.nickname,
            profile:'',
            like:[],
            comment:0,
            imageName:this.newImg,
            imageBLOB:this.newImgBlob,
            image:this.newImgsrc,
            content:this.newText,
            regtime:"방금",
          }
          this.feeds.unshift(newArticle)
          this.newText = ""
        },
        (err) => {
          console.log(err)
        }
      )
    },
    changeModal(feed) {
      this.modal=!this.modal
      this.selected = feed
      this.selidx = this.feeds.indexOf(feed)
      // this.selected.no = feed.no
      // this.selected.id = feed.id
      // this.selected.regtime = feed.regtime
      // this.selected.like = feed.like
      // this.selected.content = feed.like
      getComment(
        feed.no,
        (res) => {
          console.log(res.data)
          this.comments = res.data
        },
        (err) => {
          console.log(err)
        }
      )
    },
    editModal(feed) {
      this.selected = feed
      this.selidx = this.feeds.indexOf(feed)
      this.editmodal=!this.editmodal
    },
    updateFeed(selected) {
      let no = selected.no
      let content = selected.content
      let image = selected.imageName
      if (content !== null) {
        updateArticle(
          no,content,image,
          (res) => {
            alert("글이 수정되었습니다")
            this.editmodal = !this.editmodal
            console.log(res.data)
            this.feeds[this.selidx] = selected
          },
          (err) => {
            console.log(err)
          }
        )
      } else {
        alert("글 내용이 없습니다")
      }
    },
    deleteFeed(select) {
      deleteArticle(
        select.no,
        (res) => {
          let idx = this.feeds.indexOf(select)
          this.feeds.splice(idx,1)
          this.editmodal = !this.editmodal
          console.log(res.data)
        },
        (err) => {
          console.log(1)
          console.log(err)
        }
      )
    },
    likeFeed(feed) {
      let userid = this.user.userid
      let no = feed.no
      if (feed.like.includes(userid)){
        unlikeArticle(
          userid,no,
          (res) => {
            console.log(res.data)
            let idx = feed.like.indexOf(userid)
            feed.like.splice(idx,1)
          },
          (err) => {
            console.log(err)
          })
      } else {
        likeArticle(
          userid,no,
          (res) => {
            console.log(res.data)
            feed.like.push(userid)
          },
          (err) => {
            console.log(err)
          }
        )
      }
    },
    unlikeFeed(userid, no){
      unlikeArticle(
        userid,no,
        (res) => {
          console.log(res.data)
        },
        (err) => {
          console.log(err)
        }
      )
    },
    likeUsers(no){
      getLikeUsers(
        no,
        (res) => {
          console.log(res.data)
        },
        (err) => {
          console.log(err)
        }
      )
    },
    async upload(event){
      this.dialog = false
      event.preventDefault();
      let file = new FormData();
      file.append('file', this.files)
      // file.append('asdf','asdf')

      await axios.post('http://i4d102.p.ssafy.io/ssac/file/newsfeedImageAdd',
        file, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then( response => {
        console.log('SUCCESS!!');
        console.log(response.data)
        this.newImg = response.data.filename
        this.newImgBlob = response.data.blob
        this.newImgsrc = this.changeBlob(response.data.blob)
      })
      .catch( error => {
        console.log('FAILURE!!');
        console.log(error)
        // console.log(this.files)
        // console.log(file)
      });
      this.files = ""
    },
    async upload2(event){
      this.dialog = false
      event.preventDefault();
      let file = new FormData();
      file.append('file', this.files)
      // file.append('asdf','asdf')

      await axios.post('http://i4d102.p.ssafy.io/ssac/file/newsfeedImageAdd',
        file, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then( response => {
        console.log('SUCCESS!!');
        // console.log(response.data)
        this.selected.imageName = response.data.filename
        this.selected.imageBLOB = response.data.blob
        this.selected.image = this.changeBlob(response.data.blob)
      })
      .catch( error => {
        console.log('FAILURE!!');
        console.log(error)
        // console.log(this.files)
        // console.log(file)
      });
      this.files = []
    },
    changeBlob(data){
      if (data.length !==0){
        const byteCharacters = window.atob(data)
        const byteNumbers = new Array(byteCharacters.length)
        for (let i = 0; i< byteCharacters.length; i++){
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
  
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray],{type:"image/jpg"})
        const url = window.URL.createObjectURL(blob)

        // console.log(4)
        return url
      } else {
        return ""
      }
    },
    isLike(like){
      if(like.includes(this.user.userid)){
        return "pink"
      } else {
        return ""
      }
    },
    timeForToday(value) {
      const today = new Date();
      const timeValue = new Date(value);

      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1 || !betweenTime) return '방금전';
      if (betweenTime < 60) {
          return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
          return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
          return `${betweenTimeDay}일전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년전`;
    }

  },
}
</script>

<style>

</style>