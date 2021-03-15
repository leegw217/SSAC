<template>
  <div>
    <!-- 검색창 -->
    <div class="mt-7">
      <v-row 
        align="center"
        justify="space-around"
      >
        <v-col class="ml-2">
          <v-text-field
            label="검색어를 입력하세요."
            v-model="search"
            class="ml-8"
          ></v-text-field>
        </v-col>
        <v-col cols=3>
          <v-btn
            @click="realSearch"
            icon
          >
            <v-icon color="#536DFE"> mdi-magnify </v-icon>
          </v-btn>
        </v-col>
      </v-row>

    </div>
    <v-container cols=9 class="mt-0 pt-0">

    <v-list subheader>
      <p v-if="searched"><v-subheader>유저 검색 결과</v-subheader></p>

      <userSearchResult 
        v-for="(user,idx) in users"
        :key="idx"
        :user="user"
        />
    </v-list>

    <v-list subheader>
      <p v-if="searched"><v-subheader>팀 검색 결과</v-subheader></p>
      <teamSearchResult 
        v-for="(team,idx) in teams"
        :key="idx"
        :team="team"
        />
    </v-list>
  </v-container>

  </div>
</template>

<script>
import { mdiMagnify } from '@mdi/js'

import userSearchResult from './userSearchResult.vue'
import teamSearchResult from './teamSearchResult.vue'
import axios from 'axios'
export default {
  name: "realSearch",
  components: {
    userSearchResult,
    teamSearchResult,
  },
  data: function() {
    return {
      icons: {
        mdiMagnify,

      },
      search: '',
      users: [
        // {img: 'src', name: 'user1'}, {img: 'src', name: 'user2'}, {img: 'src', name: 'user3'}
        ],
      teams: [
        // {name: "T2", img: "https://placekitten.com/300/300" , nickname: "", followers: 14565, category: "축구"},
        // {name: "GEN.G", img: "https://placekitten.com/300/300" , nickname: "", followers: 14565, category: "lol"}, 
        // {name: "한화생명", img: "https://placekitten.com/300/300", nickname: "", followers: 14565, category: "농구"},
        // {img: 'src', name: 'team2', sport: '야구', people: '2'}
        ],
      searched: false,
    }
  },
  computed: {
    userid: function () {
      return this.$store.state.user.userid
    }
  },
  methods: {
    
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

    
    realSearch: function() {
      console.log('realSearch')
      let userid= this.userid
      let search= this.search

      axios.get(`http://i4d102.p.ssafy.io:9000/ssac/search/search?userid=${userid}&search=${search}`)
        .then(response => {
          console.log(response.data)
          // axios 요청부분 - 요청한번보내서 this.users랑 this.teams를 다 업데이트.
          this.users = response.data.users
          this.teams = response.data.teams
          this.searched = response.data.searched // 유저검색결과 한 줄 
          for (let idx = 0; idx<this.teams.length; idx++){
          if (this.teams[idx].logo) {
            this.teams[idx].logo = this.changeBlob(this.teams[idx].logo)
          } else {
            this.teams[idx].logo=''
          }}      
        })
        .catch(error => {
          console.error(error)
        })
    },
  }
}
</script>

<style>

</style>