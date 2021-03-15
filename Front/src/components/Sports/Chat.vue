<template>
  <div>
    <v-fab-transition>
      <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
                color="#8187ff"
                dark
                absolute
                fab
                right
                v-bind="attrs" v-on="on" @click="makechat"
              >
                <v-icon>mdi-plus</v-icon>
              </v-btn>

        </template>
        <v-card>
          <v-toolbar dark color="#8187ff">
            <v-btn icon dark @click="dialog = false">
              <v-icon>mdi-close</v-icon>
            </v-btn>
            <v-toolbar-title>채팅 만들기</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items @click="dialog = false">
              <!--  -->
              <v-btn dark text @click="makechatroom">
                <v-icon
                  dark
                  right
                >
                  mdi-check
                </v-icon>
              </v-btn>
              <!--  -->
            </v-toolbar-items>
          </v-toolbar>
          <v-list three-line subheader>
            <v-subheader>대상 경기</v-subheader>
            <v-list-item>
              <v-list-item-content>
                <v-container v-for="(game, idx) in games" :key="idx">
                  <v-card :elevation="10" class="rounded-xl text-center align-center pa-0 mx-0" link
                    @click="teamselect(game)">
                    <v-img 
        height="65"
        v-if="game.includes('담원') || game.includes('케이티') || game.includes('T1') || game.includes('DRX')" 
        :src="photos[5]"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>
        <v-img 
        height="65"
        v-else-if="game.includes('젠지') || game.includes('아프리카') || game.includes('프레딧') || game.includes('농심') || game.includes('한화생명') || game.includes('리브')" 
        :src="photos[4]"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>
        <v-img 
        height="65"
        v-else-if="game.includes('kt') || game.includes('sk') || game.includes('두산') || game.includes('삼성') || game.includes('LG')" 
        :src="photos[3]"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>
        <v-img 
        height="65"
        v-else-if="game.includes('롯데') || game.includes('기아') || game.includes('키움') || game.includes('NC')" 
        :src="photos[2]"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>
        <v-img 
        height="65"
        v-else-if="game.includes('대구') || game.includes('제주') || game.includes('경남') || game.includes('서울')" 
        :src="photos[1]"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>
        <v-img 
        height="65"
        v-else-if="game.includes('전북') || game.includes('인천') || game.includes('강원') || game.includes('수원') || game.includes('전북')" 
        :src="photos[0]"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>
        <v-img 
        height="65"
        v-else 
        :src="logo.src"><v-card-title class="justify-center">
                        <div class="headerClass white--text">
                          {{game}}
                        </div>
                      </v-card-title></v-img>

                  </v-card>
                </v-container>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
          <v-list three-line subheader>
            <v-subheader>초대할 멤버</v-subheader>
            <v-list-item>
              <v-container class="pa-1" v-for="(member, idx) in members" :key="idx">
                <div  @click="memberselect(member.nickname,member.id)">
                  <v-chip style="width:50px" class=" ma-1 pa-1 justify-center">{{member.nickname}}</v-chip>
                </div>
              </v-container>
            </v-list-item>
          </v-list>
        </v-card>
      </v-dialog>
    </v-fab-transition>
    <h2 class="ml-8 mt-4 mb-0 grey--text text--darken-2" style="font-weight: bolder" >Today's</h2>
    <h1 class="ml-8 mb-4">Sports Matches</h1>
    <v-carousel
    class="mt-6"
    cycle
    height="400"
    hide-delimiter-background
    show-arrows-on-hover
    >
    <v-carousel-item v-for="(chat,idx) in chats" :key="idx" class="mb-0">
      <v-card link color="white " class="black--text mx-auto rounded-xl" height="80%" width="80vw" dark @click="footerOut(); selectChat(chat.room.roomId); gotoChat(idx)">
        <v-img 
        height="65%"
        v-if="chat.room.name.includes('담원') || chat.room.name.includes('케이티') || chat.room.name.includes('T1') || chat.room.name.includes('DRX')" 
        :src="photos[5]"></v-img>
        <v-img 
        height="65%"
        v-else-if="chat.room.name.includes('젠지') || chat.room.name.includes('아프리카') || chat.room.name.includes('프레딧') || chat.room.name.includes('농심') || chat.room.name.includes('한화생명') || chat.room.name.includes('리브')" 
        :src="photos[4]"></v-img>
        <v-img 
        height="65%"
        v-else-if="chat.room.name.includes('kt') || chat.room.name.includes('sk') || chat.room.name.includes('두산') || chat.room.name.includes('삼성') || chat.room.name.includes('LG')" 
        :src="photos[3]"></v-img>
        <v-img 
        height="65%"
        v-else-if="chat.room.name.includes('롯데') || chat.room.name.includes('기아') || chat.room.name.includes('키움') || chat.room.name.includes('NC')" 
        :src="photos[2]"></v-img>
        <v-img 
        height="65%"
        v-else-if="chat.room.name.includes('대구') || chat.room.name.includes('제주') || chat.room.name.includes('경남') || chat.room.name.includes('서울')" 
        :src="photos[1]"></v-img>
        <v-img 
        height="65%"
        v-else-if="chat.room.name.includes('전북') || chat.room.name.includes('인천') || chat.room.name.includes('강원') || chat.room.name.includes('수원') || chat.room.name.includes('전북')" 
        :src="photos[0]"></v-img>
        <v-img 
        height="65%"
        v-else 
        :src="logo.src"></v-img>
        <v-card-title style="overflow: hidden; text-overflow: ellipsis;" class="headline">
          {{ chat.room.name }}
        </v-card-title>
        <v-card-subtitle>
          <v-chip v-for="(name, idx) in chat.usernames" :key="idx" class="ma-2" x-small>
            {{ name }}
          </v-chip>
        </v-card-subtitle>
      </v-card>
    </v-carousel-item>
  </v-carousel>
    
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {listChatroom, createChat, registerChat} from '@/api/sports/chat.js'


export default {
  name:"chat",
  data () {
      return {
        dialog: false,
        notifications: false,
        sound: true,
        widgets: false,
        idx:0,
        // 채팅방 이름
        name: '',
        //채팅멤버 아이디 배열
        ids: [this.$store.state.user.userid],
        //채팅멤버 닉네임 배열
        nicknames: [this.$store.state.user.nickname],
        chats: [],
        // 여기는 채팅만드는 팀
        games: [],
        members: [],
        img: '',
        logo : { src : require("@/assets/images/싹7(롤기죽임).jpg") },
      }
    },
    created() {
      listChatroom(
      this.$store.state.user.userid,
      (response) => {
        console.log(response);
        this.chats = response.data 
      },
      (error) => {
        console.log(error)
      });



    },
    computed: {
      ...mapState([ 'photos']),
    },
    methods: {
      gotoChat(idx) {
        this.$router.push(`chat-room/${idx}`)
      },
      footerOut: function () {
        this.$store.dispatch('footerOut');
        console.log(this.$store.state.isChatRoom);
      },
      selectChat(roomid) {
        console.log(roomid);
        // this.$store.state.chat.roomid = roomid
        this.$store.dispatch("saveChatroom",roomid)
      },
      teamselect(game) {
        this.name = game
        console.log(this.name);
      },
      memberselect(nickname, id) {
        if (this.nicknames.indexOf(nickname) > 0){
          console.log('duplicated!!!!');
        }else{
        this.nicknames.push(nickname)
        this.ids.push(id)
        console.log(this.ids);
        console.log(this.nicknames);
        }
      },
      makechat() {
        createChat(
          this.$store.state.user.userid,
          (response) => {
            console.log(response);
            this.games = response.data.game
            this.members = response.data.follow            
          },
          (error) => {
            console.log(error)
          });
      },
      makechatroom() {
        let para = {
          name: this.name,
          ids: this.ids,
          nicknames: this.nicknames
        };
        registerChat(
          para,
          (response) => {
            console.log(response);
            console.log(para);
            // this.$router.push({name:"SportsChat"})
            listChatroom(
              this.$store.state.user.userid,
              (response) => {
                console.log(response);
                this.chats = response.data 
              },
              (error) => {
                console.log(error)
              });
            },  
          (error) => {
            console.log(error)
          });

      }
    }
}
</script>

<style>
.v-list-item {
  border-bottom: 1px;
  border-bottom-color: black
}
.rounded-card{
    border-radius:20px;
}
.headerClass{
    white-space: nowrap ;
    word-break: normal;
    overflow: hidden ;
    text-overflow: ellipsis;
}
</style>