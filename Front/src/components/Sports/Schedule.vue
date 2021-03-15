<template>
  <div class="mt-7 ml-2 mr-2 mb-3">
    <v-sheet height="362">
      <div v-if="todayEvents.length > 0">
        <v-row class="mr-1" justify="space-between">
          <h3 class="ml-5 grey--text text--darken-2" style="font-weight: bolder">Today's</h3>
          <v-btn icon @click="setToday" color="#8187ff">
            <v-icon> mdi-calendar-refresh </v-icon>
          </v-btn>
        </v-row>
        <h2 class="ml-2 mb-2">Sports Matches</h2>        
      <v-carousel
        cycle
        height="300"
        hide-delimiter-background
        show-arrows-on-hover
        light
        hide-delimiters        
      >
        <v-carousel-item
          v-for="(event, i) in todayEvents"
          :key="i"
        >
          <v-sheet 
            height="100%"
          >
            <v-card
              class="mx-auto"
              max-width="344"
            >
              <v-avatar
                  size="172"
                  tile
              >
                <v-img :src="changeBlob(event.team1_logo)"></v-img>
              </v-avatar>
              <v-avatar
                  size="172"
                  tile
              >
                <v-img :src="changeBlob(event.team2_logo)"></v-img>
              </v-avatar>

              <v-card-title>
                {{ event.name }}
              </v-card-title>

              <v-card-subtitle>
                {{ event.start }}
              </v-card-subtitle>
            </v-card>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>
      </div>
    </v-sheet>
    <!-- 팀 필터 -->
    <v-sheet class="mb-5">
        <h3 class="ml-2 mb-4 grey--text text--darken-2" style="font-weight: bolder" >My teams</h3>
      <v-card class="px-5">
        <v-row>
          <v-col cols=3>
            <v-img @click="filterMyteam(0)" class="d-flex rounded-circle" width="60px" height="60px" :src="logo.src"></v-img>
            <h4 class="mt-1" justify="center" align="center">전체</h4>
          </v-col>
          <v-col cols=3 
            v-for="(myteam,idx) in myteamss"
            :key=idx
          >
            <img @click="filterMyteam(myteam.team_no)" class="d-flex rounded-circle" width="60px" height="60px" :src="changeBlob(myteam.logo)" alt="team">
            <h4 class="mt-1" justify="center" align="center">{{teamname[myteam.team_no]}}</h4>          
          </v-col>
        </v-row>
      </v-card>
    </v-sheet>
    <v-sheet>
      <v-toolbar
        flat
      >
      <v-row align="center" justify="space-around">

        <v-btn
          icon
          @click="prev"
          color="#8187ff"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
        <v-toolbar-title v-if="$refs.calendar">
          {{ $refs.calendar.title }}
        </v-toolbar-title>
        <v-btn
          icon
          @click="next"
          color="#8187ff"
        >
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-row>
      </v-toolbar>
    </v-sheet>
    <v-sheet height="600">
      <v-calendar
        categories="test01"
        ref="calendar"
        v-model="focus"
        :events="events"
        :event-color="getEventColor"

        @click:event="showEvents"
        @click:more="viewDay"
        @click:date="viewDay"
        :type="type[tog]"
      ></v-calendar>
    </v-sheet>

    <!-- Daily 일정 -->
    <v-expand-transition>
      <v-card
        v-show="expandDaily"
        width="100%"
        class="mx-auto mt-2 mb-2 indigo lighten-5 text-center"
        elevation="2"
      >
        <div class="pl-6 py-3 indigo accent-1 white--text" @click="closeDaily">
          <v-row class="ml-16" align="center" justify="center">
            <h2>{{ focus }}</h2>
            <v-btn
              class="ml-15" 
              @click="closeDaily"
              icon
            >
              <v-icon>
                mdi-window-close
              </v-icon>
            </v-btn>
          </v-row>
        </div>
        <v-list-item
          v-for="(event, i) in filterDaily(focus)"
          :key="i"
        >
          <v-list-item-content>
            <v-list-item-title class="text-center rounded-xl" @click="changeShow(i)">
              <v-card :elevation="10" class="rounded-xl text-center align-center pa-0 mx-0" link>
                <v-img 
                  height="65"
                  v-if="event.name.includes('담원') || event.name.includes('케이티') || event.name.includes('T1') || event.name.includes('DRX')" 
                  :src="photos[5]"><v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
                <v-img 
                  height="65"
                  v-else-if="event.name.includes('젠지') || event.name.includes('아프리카') || event.name.includes('프레딧') || event.name.includes('농심') || event.name.includes('한화생명') || event.name.includes('리브')" 
                  :src="photos[4]"
                >
                  <v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
                <v-img 
                  height="65"
                  v-else-if="event.name.includes('kt') || event.name.includes('sk') || event.name.includes('두산') || event.name.includes('삼성') || event.name.includes('LG')" 
                  :src="photos[3]"
                >
                  <v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
                <v-img 
                  height="65"
                  v-else-if="event.name.includes('롯데') || event.name.includes('기아') || event.name.includes('키움') || event.name.includes('NC')" 
                  :src="photos[2]"
                >
                  <v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
                <v-img 
                  height="65"
                  v-else-if="event.name.includes('대구') || event.name.includes('제주') || event.name.includes('경남') || event.name.includes('서울')" 
                  :src="photos[1]"
                >
                  <v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
                <v-img 
                  height="65"
                  v-else-if="event.name.includes('전북') || event.name.includes('인천') || event.name.includes('강원') || event.name.includes('수원') || event.name.includes('전북')" 
                  :src="photos[0]"
                >
                  <v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
                <v-img 
                  height="65"
                  v-else 
                  :src="logo.src"
                >
                  <v-card-title class="justify-center">
                    <div class="headerClass white--text">
                      {{event.name}}
                    </div>
                  </v-card-title>
                </v-img>
              </v-card>
            </v-list-item-title>

            <!-- 배팅 -->
            <v-expand-transition>
              <v-card
                v-show="expandGame[i]"
                class="mt-1"
              >
                <v-card-text>
                  <!-- 관리(경기) 상태 -->
                  <v-row justify="center">
                    <v-col 
                      v-if="event.betDone.length === 4"
                      cols=4
                    >
                      Bat Over
                    </v-col>
                    <v-col 
                      v-if="event.gameDone.length === 4"
                      cols=4
                    >
                      Game Over
                    </v-col>
                    <v-col 
                      v-if="event.calDone.length === 4"
                      cols=4
                    >
                      Cal Over
                    </v-col>
                    <v-col cols=12>
                      <v-btn
                        @click="endBetting(event)"
                        v-if="mas"
                      >
                        Bat
                      </v-btn>
                      <v-btn
                        @click="endtheGame(event)"
                        v-if="mas"
                      >
                        Game
                      </v-btn>
                      <v-btn
                        @click="calculEvent(event)"
                        v-if="mas"
                      >
                        Accounts
                      </v-btn>
                    </v-col>
                  </v-row>
                  <!-- 팀 매치 -->
                  <v-row>
                    <v-col cols=5 class="px-0">
                      <span><h2>{{teamname[event.team1_id]}}</h2></span>
                    </v-col>
                    <v-col cols=2 class="px-0">
                      {{event.team1_score}} vs {{event.team2_score}}
                    </v-col>
                    <v-col cols=5 class="px-0">
                      <span><h2>{{teamname[event.team2_id]}}</h2></span>
                    </v-col>
                  </v-row>
                  <!-- 투표 -->
                  <v-row>
                    <v-col cols=6>
                      <v-btn
                        @click="editScore(event,2,-1)"
                        v-if="mas"
                      >
                        -
                      </v-btn>
                      <v-btn
                        @click="editScore(event,2,1)"
                        v-if="mas"
                      >
                        +
                      </v-btn>
                      <v-btn
                        class="white--text"
                        :disabled="isDisabled(event,event.team2_user)"
                        @click="betTeam(event,2)"
                        color="#536DFE"
                      >
                        Vote
                      </v-btn>
                    </v-col>
                    <v-col cols=6>
                      <v-btn
                        class="white--text"
                        @click="editScore(event,1,-1)"
                        v-if="mas"
                      >
                        -
                      </v-btn>
                      <v-btn
                        @click="editScore(event,1,1)"
                        v-if="mas"
                      >
                        +
                      </v-btn>
                      <v-btn
                        class="white--text"
                        :disabled="isDisabled(event,event.team1_user)"
                        @click="betTeam(event,1)"
                        color="#536DFE"
                      >
                        Vote
                      </v-btn>
                    </v-col>
                    <v-col cols=12>
                      무승부
                      <br>
                      <v-btn
                        class="white--text"
                        :disabled="isDisabled(event,event.draw_user)"
                        @click="betTeam(event,3)"
                        color="#536DFE"
                        width="224"
                      >
                        Vote
                      </v-btn>
                    </v-col>
                  </v-row>                  
                  <!-- 배팅 정보 -->
                  <v-row>
                    <v-col>
                      투표비율({{teamname[event.team1_id]}} : {{teamname[event.team2_id]}} : 무승부): {{event.team1_user.length}} : {{event.team2_user.length}} : {{event.draw_user.length}}
                      <br>
                      예상획득포인트({{teamname[event.team1_id]}}) : {{ betRatio(event.team1_user.length,event.team2_user.length,event.draw_user.length,event.team1_user.length) }}
                      <br>
                      예상획득포인트({{teamname[event.team2_id]}}) : {{ betRatio(event.team1_user.length,event.team2_user.length,event.draw_user.length,event.team2_user.length) }}
                      <br>
                      예상획득포인트(무승부) : {{ betRatio(event.team1_user.length,event.team2_user.length,event.draw_user.length,event.draw_user.length) }}
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-card>
            </v-expand-transition>

          </v-list-item-content>
        </v-list-item>
      </v-card>
    </v-expand-transition>
    <br>
    <br>
    <br>
    <br>
  </div>
</template>

<script>
import { getTodayEventImage, getMyTeam, getSchedule, pmScore, betGame, endBet, endGame, calculPts } from "@/api/tabs/sports.js"
import { mapState } from 'vuex' 
// 
  export default {
    name:"Schedule",
    data: () => ({
      mas:false,
      logo : { src : require("@/assets/images/싹7(롤기죽임).jpg") },
      myteamss:[],
      myteams:[],
      teamname:{
        1: '강원', 2: '광주', 3: '대구', 4: '서울', 5: '성남', 6: '수원', 7: '수원FC', 8: '울산', 9: '인천', 10: '전북', 11: '제주', 12: '포항',
        23: '두산', 24: '롯데', 25: '삼성', 26: '키움', 27: '한화', 28: 'KIA', 29: 'KT wiz', 30: 'LG', 31: 'NC', 32: 'SK',
        33: '담원 기아', 34: 'DRX', 35: '젠지', 36: '아프리카', 37: 'T1', 38: 'KT', 39: '리브 샌박', 40: '농심', 41: '한화생명', 42: '프레딧'
      },
      expandGame:[false,false,false,false,false,false,false,false,false,false],
      expandDaily:false,
      dialog:false,
      focus: '',
      tog:true,
      type: {
        true:"month",
        false:"day",
      },
      selectedEvent: {
        id: Number,
        events_no: Number,
        name: String, // (달력에 표시할 이름) 
        start: String, // ('YYYY-MM-DD')
        start_time: String, // ("hh-mm")
        team1_id: Number, // (팀1 고유번호)
        team2_id: Number, // (팀2 고유번호)
        team1_score: Number, // (팀1 점수)
        team2_score: Number, // (팀2 점수)
        team1_user: Array,  // (팀1에 베팅한 유저 고유번호 리스트)
        team2_user: Array, // (팀2에 베팅한 유저 고유번호 리스트)
        draw_user: Array, // (무승부에 베팅한 유저 고유번호 리스트)
        betDone: "false", // (베팅 끝났는지)
        gameDone: "false", // (게임 끝났는지)
        calDone: "false", // (정산 끝났는지)
      },
      selectedElement: null,
      selectedOpen: false,
      colors: ['grey darken-1','blue','red', 'green'],
      originevents:[
      ],
      events:[],
      today:'',
      hm:'',
      todayEvents: [],
    }),
    computed: {
      ...mapState([ 'photos']),
    },
    mounted () { 
      console.log(this.$store.state.user)
      console.log(this.$store.state.isLogin)
      if (this.$store.state.user.grade === 10) {
        this.mas = true
      } else {
        this.mas = false
      }
      this.$refs.calendar.checkChange()
      getSchedule(
        (res) => {
          // console.log(res.data)
          this.originevents = res.data
          this.events = this.originevents
        },
        (err) => {
          console.log(err)
        }
      )
      let now = new Date()
      let month = now.getMonth()+1
      let date = now.getDate()

      month = `${month}`
      date = `${date}`
      if (month.length === 1){
        month = `0${month}`
      }
      if (date.length === 1){
        date = `0${date}`
      }

      let time = `${now.getFullYear()}-${month}-${date}`
      console.log(time)
      // let hourmin = `${now.getHours()}:${now.getMinutes()}`
      let h = now.getHours()
      let m = now.getMinutes()
      h = `${h}`
      m = `${m}`
      if (h.length === 1){
        h = `0${h}`
      }
      if (m.length === 1){
        m = `0${h}`
      }
      this.today = time
      this.focus = time
      this.hm = `${h}:${m}`
      console.log(this.today)
      // console.log()
      console.log(this.hm)
      // console.log(this.hm < '19:00')
      // this.events = this.originevents
      getMyTeam(
        this.$store.state.user.userid,
        (res) => {
          this.myteamss = res.data
          console.log(res.data)
        },
        (err) => {
          console.log(err)
        }
      )
      getTodayEventImage(
        (res) => {
          this.todayEvents = res.data
        },
        (err) => {
          console.log(err)
        }
      )

    },
    methods: {
      toggle () {
        this.tog = !this.tog
      },
      viewDay ( {date} ) {
        this.focus = date
        this.resetShow()
        let items = this.filterDaily(date)
        if (items.length > 0){
          this.expandDaily = true
        }
      },
      getEventColor (event) {
        return this.colors[event.events_no]
      },
      setToday () {
        this.focus = this.today
      }, 
      prev () {
        this.$refs.calendar.prev()
      },
      next () {
        this.$refs.calendar.next()
      },
      showEvents ({event}) {
        let ev = {
          date:event.start
        }
        this.viewDay(ev)
      },
      closeDaily () {
        this.expandDaily = false
        this.resetShow()
      },
      filterDaily : function(focus) {
        // console.log(focus)
        return this.events.filter(function(ev){
          return ev.start.includes(focus)
        })
      },
      changeShow(idx) {
        this.$set(this.expandGame, idx, !this.expandGame[idx])
      },
      resetShow: function() {
        for(let idx = 0; idx < 10; idx++) {
          this.$set(this.expandGame, idx, false)
        }
      },
      betRatio(team1,team2,draw,want) {
        let pts = Math.round((team1+team2+draw-want)/((team1+team2+draw)*2)*100)
        if (team1+team2+draw === 0) {
          return 0
        }
        if (pts === 0) {
          return 1
        } else {
          return pts
        }
      },
      isDisabled(event,want) {
        let userid = this.$store.state.user.userid
        if ((event.betDone.length === 4) || (event.gameDone.length === 4)) {
          return true
        }
        if (event.team1_user.includes(userid) || event.team2_user.includes(userid) || event.draw_user.includes(userid)) {
          if (want.includes(userid)) {
            return false
          } else {
            return true
          }
        } else {
          return false
        }
      },
      betTeam(event,n) {
        let userid = this.$store.state.user.userid
        let want = []
        if (n === 1) {
          want = event.team1_user
        } else if (n === 2) {
          want = event.team2_user
        } else {
          want = event.draw_user
        }
        betGame(
          event.id,userid,n,
          (res) => {
            console.log(res.data)
            if (want.includes(userid)) {
              console.log("빼기")
              let idx = want.indexOf(userid)
              want.splice(idx,1)
            } else {
              console.log("더하기")
              want.push(userid)
            }
          },
          (err) => {
            console.log(err)
          }
        )

      },
      endBetting(event) {
        if (event.gameDone.length === 4) {
          console.log("게임 끝을 풀어줭")
        } else {
          let bool = event.betDone.length
          if (bool === 4) {
            bool = "false"
          } else {
            bool = "true"
          }
          endBet(
            event.id,bool,
            (res) => {
              console.log(res.data)
              if (bool.length === 4) {
                event.betDone = "true"
              } else {
                event.betDone = "false"
              }
            },
            (err) => {
              console.log(err)
            }
          )
        }
      },
      endtheGame(event) {
        if (event.betDone.length === 5) {
          if (event.gameDone.length === 5) {
            event.gameDone = "false"
          } else {
            console.log("베팅 먼저 끝내세용")
          }
        } else {
          let bool = event.gameDone.length
          if (bool === 4) {
            bool = "false"
          } else {
            bool = "true"
          }
          endGame(
            event.id,bool,
            (res) => {
              console.log(res.data)
              if (bool.length === 4) {
                event.gameDone = "true"
              } else {
                event.gameDone = "false"
              }
            },
            (err) => {
              console.log(err)
            }
          )
          console.log("게임끝")
        }
      },
      calculEvent(event) {
        if ((event.betDone.length === 4) && (event.gameDone.length === 4) && (event.calDone.length === 5)) {
          let users = []
          if (event.team1_score > event.team2_score) {
            users = event.team1_user
          } else if(event.team1_score < event.team2_score) {
            users = event.team2_user
          } else {
            users = event.draw_user
          }
        
          let pts = this.betRatio(event.team1_user.length,event.team2_user.length,event.draw_user.length,users.length)
          calculPts(
            event.id,users,pts,
            (res) => {
              console.log(res.data)
              event.calDone = "true"
            },
            (err) => {
              console.log(err)
            }
          )
        } else {
          alert("베팅, 게임먼저 끝내세용")
        }
      },
      editScore(event,n,pm) {
        console.log(event.id,n,pm)
        pmScore(
          event.id,n,pm,
          (res) => {
            if (n === 1 && pm === 1) {
              event.team1_score += 1
            } else if (n === 1 && pm === -1) {
              event.team1_score -= 1
            } else if (n === 2 && pm === 1) {
              event.team2_score += 1
            } else {
              event.team2_score -= 1
            }
            console.log(res.data)
            
          },
          (err) => {
            console.log(err)
          }
        )
      },
      filterMyteam(number) {
        if (number === 0) {
          this.myteams = []
        } else {
          if (this.myteams.includes(number)){
            let idx = this.myteams.indexOf(number)
            this.myteams.splice(idx,1)
          } else {
            this.myteams.push(number)
          }  
        }
        if (this.myteams.length === 0) {
          this.events = this.originevents
        } else {
          this.events = this.filterteam(this.myteams)
        }
      },
      filterteam : function(myteams) {
        // console.log(focus)
        return this.originevents.filter(function(ev){
          return myteams.includes(ev.team1_id) || myteams.includes(ev.team2_id)
        })
      },
      master() {
        this.mas = !this.mas
      },
      changeBlob(data){
        const byteCharacters = window.atob(data)
        const byteNumbers = new Array(byteCharacters.length)
        for (let i = 0; i< byteCharacters.length; i++){
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray],{type:"image/jpg"})
        const url = window.URL.createObjectURL(blob)

        return url
      },

    }, 
  }
</script>
