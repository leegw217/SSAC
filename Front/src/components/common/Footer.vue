<template>

    <v-footer
      fixed
      :padless="padless"
      class="ml-2 mr-2 mb-2 opa rounded-xl"
    >
      <v-card
        width="100%"
        class="rounded-xl"
      >
        <v-card-text>
          <v-row>
            <v-col>
              <v-btn
                icon
                @click="gotoSports"
              >
                <v-icon size="24px">
                  {{ icons[0] }}
                </v-icon>
              </v-btn>
            </v-col>
            <v-col>
              <v-btn
                icon
                @click="gotoFeed"
              >
                <v-icon size="24px">
                  {{ icons[1] }}
                </v-icon>
              </v-btn>
            </v-col>
            <v-col>
              <v-btn
                icon
                @click="gotoSearch"
              >
                <v-icon size="24px">
                  {{ icons[2] }}
                </v-icon>
              </v-btn>
            </v-col>
            <v-col>
              <v-btn
                icon
                @click="gotoProfile"
              >
                <v-icon size="24px">
                  {{ icons[3] }}
                </v-icon>
              </v-btn>
            </v-col>
            <v-col>
              <v-dialog
                v-model="dialog"
                width="500"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-btn 
                    icon
                    v-bind="attrs"
                    v-on="on"
                    @click="updateAlert"
                    ><v-icon>{{ icons[4] }}</v-icon>
                  </v-btn>
                </template>

                <v-card>
                  <v-card-title class="headline deep-purple accent-3 white--text">
                    <div class="mx-auto">
                      {{this.$store.state.user.nickname}}님을 위한 알림
                    </div>
                  </v-card-title>
                  <br>

                  <v-card-text class="p-2">
                    <div
                      v-for="(item,idx) in alertList"
                      :key=idx
                      class=""
                    >
                      <v-card  
                        class="pa-2 elevation-5"
                      >
                        <div class="text--primary">{{item.content}}</div>
                        <div class="text-right ma-1" >{{timeForToday(item.regtime)}}</div>
                      </v-card>
                      <v-divider class="my-3"></v-divider>
                    </div>
                  </v-card-text>

                </v-card>
              </v-dialog>
            </v-col>
          </v-row>
        </v-card-text>

      </v-card>


    </v-footer>


</template>

<script>
import {getAlert} from "@/api/common.js"

export default {
  name:"Footer",
  data () {
    return {
      icons: [
        'mdi-home',
        'mdi-text-box',
        'mdi-magnify',
        'mdi-account',
        'mdi-bell-outline',
      ],
      padless: true,
      rounded: true,
      dialog: false,
      funcs: [
        "gotoSports",
        "gotoFeed",
        "gotoSearch",
        "checkAlert",
        "gotoProfile",
      ],
      userid: this.$store.state.user.userid,
      alertList:['ㅇㅇ님이 회원님의 게시글을 좋아합니다.','ㅇㅇ님이 댓글을 달았습니다','ㅇㅇ님이 댓글을 달았습니다.','ㅇㅇ님이 회원님의 게시글을 좋아합니다.','ㅇㅇ님이 댓글을 달았습니다','ㅇㅇ님이 댓글을 달았습니다.','ㅇㅇ님이 회원님의 게시글을 좋아합니다.','ㅇㅇ님이 댓글을 달았습니다','ㅇㅇ님이 댓글을 달았습니다.','ㅇㅇ님이 회원님의 게시글을 좋아합니다.','ㅇㅇ님이 댓글을 달았습니다','ㅇㅇ님이 댓글을 달았습니다.',],
    }
  },
  methods: {
    gotoSports() {
      if (this.$route.path !== "/tabs") {
        this.$router.push({name:'SportsSchedule'})
      }
    },
    gotoFeed() {
      if (this.$route.path !== "/tabs/feed") {
        this.$router.push({name:'Feed'})
      }
    },
    gotoSearch() {
      if (this.$route.path !== "/tabs/search") {
        this.$router.push({name:'realSearch'})
      }
    },
    checkAlert() {
      
    },
    gotoProfile() {
      if (this.$route.path !== "/profile") {
        this.$router.push({name:'Article'})
      }
    },
    updateAlert(){
    getAlert(
      this.userid,
      (res) => {
        // console.log(res.data)
        this.alertList = res.data
      },
      (err) => {
        console.log(err)
      }
    )
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
  // created() {

  // },
}
</script>

<style>
.opa {
  opacity: 0.6;
  
}

</style>