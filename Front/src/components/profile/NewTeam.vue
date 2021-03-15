<template>
  <div>

    <div class="mx-5">
      <v-text-field
        :elevation="6"
        label="검색어를 입력하세요."
        v-model="query"
      ></v-text-field>
      <hr style="height:10px; visibility:hidden;" />
      <v-row
        align="center"
        justify="space-around"
      > 
        <v-btn
          :elevation="6"
          @click="turnAll"
        >
          전체
        </v-btn>

        <v-btn
          :elevation="6"
          color="primary"
          @click="turnFootball"
        >
          축구
        </v-btn>

        <v-btn
          :elevation="6"
          color="error"
          @click="turnBaseball"
        >
          야구
        </v-btn>

        <v-btn
          :elevation="6"
          color="green darken-1 white--text"
          @click="turnLOL"
        >
          LOL
        </v-btn>

      </v-row>

    </div>
    <br>
    <v-container>
      <v-row>
        <v-col 
          style="mx-4"
          v-for="(team, idx) in filterTeam(cat,query)"
          :key="idx"
          :team="team"
        >
          <v-card
            :elevation="7"
            rounded-xl
            width="95%"
            height="80"
            hover
            class="mx-3 my-0 rounded-xl"
            tag="article"
            border-variant="success"
          >
          <v-list-item style="height:100%">
            <v-list-item-avatar
              rounded-lg
              size="60"
              color="white"
            >
              <v-img
                :src="team.logo"
                class="white--text align-end"
                height="60" 
              >
              </v-img>
            </v-list-item-avatar>

             <v-list-item-content>
              <v-list-item-title class="headline mb-1" style="padding:0px">{{ team.name }}</v-list-item-title>
              <v-list-item-subtitle >{{ team.count }} 명</v-list-item-subtitle>
            </v-list-item-content>
            <div >
              <v-btn
                icon
                dark
                small
                class=" blue rounded-circle d-inline-block"
              >
              <v-icon 
              dark
              >
                mdi-plus
              </v-icon>
              </v-btn>
            </div>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>

</template>

<script>
// import { mapState } from 'vuex'
import { listTeams } from "@/api/profile/newteam.js"

export default {
  name: "NewTeam",
  data: function () {
    return {
      name: '',
      cat: 0,
      query: "",
      teams:[]
    }
  },
  
  created() {
    listTeams(
      this.$store.state.user.userid,
      (response) => {
        console.log(response);
        this.teams = response.data
        for (let idx = 0; idx<this.teams.length; idx++){
          if (this.teams[idx].logo) {
            this.teams[idx].logo = this.changeBlob(this.teams[idx].logo)
          } else {
            this.teams[idx].logo=''
          }
      }},
      error => {
        console.log(error);
      })
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
    },
    turnAll: function() {
            this.cat = 0
        },
    turnFootball: function() {
        this.cat = 1
    },
    turnBaseball: function() {
        this.cat = 2
    },
    turnLOL: function() {
        this.cat = 3
    },
    filterTeam: function(cat,que) {
    return this.teams.filter(function(it){
        return (it.event_no*cat === 0 || it.event_no === cat) && it.name.includes(que)
    })
    },
  }
}
</script>

<style>

</style>