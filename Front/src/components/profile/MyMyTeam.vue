<template>
  <div>
    <br>
    
    <div >
    <v-dialog
     class="text-center"
      v-model="dialog"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }">
      <v-container>
      <v-row>
        <v-col 
          style="mx-4"
          v-for="(team, idx) in myteams"
          :key = "idx"
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
            @click="selectTeam(team)"
          >
            <v-list-item style="height:100%">
              <v-list-item-avatar
                @click="selectTeam(team)"
                v-bind="attrs"
                v-on="on"
                rounded-lg
                size="50"
                color="white"
              >
                <v-img
                  :src="team.logo"
                  class="white--text align-end"
                  height="60"
                >
                </v-img>
              </v-list-item-avatar>
<v-list-item-content
            @click="selectTeam(team)"
            v-bind="attrs"
            v-on="on">
              <v-list-item-title class="headline mb-1" style="padding:0px" v-if="team.name.length === 0 ">{{ team.name }}</v-list-item-title>
              <v-list-item-title style="padding:0px" v-else>{{ team.name }}</v-list-item-title>
              <v-list-item-subtitle >{{ team.count }} 명</v-list-item-subtitle>
            </v-list-item-content>
          <div >
            <v-btn
                icon
                dark
                small
                class=" red rounded-circle d-inline-block"
                @click="removeMyteam(team)"
              >
              <v-icon 
              dark
              >
                mdi-minus
              </v-icon>
              </v-btn>
          </div>
          </v-list-item>
          </v-card>

        </v-col>
      </v-row>
      </v-container>
      <br>
      <br>
      <br>
      </template>

      <v-card>
        <v-card-title class="headline blue--text lighten-2">
          {{ this.selectedData.name }}
        </v-card-title>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="별명을 입력하세요."
                  v-model="nickname"
                  required
                ></v-text-field>
                <span>체크한 이름: {{ nickname }}</span>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            :nickname = 'nickname'
            @click="dialog = false; nicknaming({selectedData,nickname});"
          >
            변경완료
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
    
  </div>
</template>

<script>
// import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'MyMyTeam',
  data () {
      return {
        dialog: false,
        selectedData: Object,
        nickname: '',
        myteams: Object,

      }
    },
  // computed: {
  //  ...mapState({
  //    myteams: 'myTeams'
  //  })
  // },

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
    selectTeam(team) {
      this.selectedData = team
    },
    nicknaming({selectedData,nickname}) {
      this.$store.dispatch('nicknaming', {selectedData,nickname});
      console.log(selectedData);
      console.log(nickname);


    console.log('created')
    const params2 = {
      params2: {
        userid: this.userid,
        name: this.nickname
      }
    }

    axios.patch(`http://i4d102.p.ssafy.io/ssac/team/myTeamNameChange`, params2)
      .then(response => {
        console.log(response.data)
        // this.articles = response.data
        // this.users = response.data.items
        // this.searched = true // 유저검색결과 한 줄 
      })
      .catch(error => {
        console.error(error)
      })
    },
    removeMyteam(team) {
    console.log(team)
    let userid= this.userid
    let no= team.no
    // console.log(JSON.stringify(params3))
    axios.delete(`http://i4d102.p.ssafy.io:9000/ssac/team/myTeamDelete?userid=${userid}&no=${no}`)
      .then(response => {
        console.log(response.data)
        // this.articles = response.data
        // this.users = response.data.items
        // this.searched = true // 유저검색결과 한 줄 
      })
      .catch(error => {
        console.error(error)
      })
    },
  },

  created() {
    console.log('created')
    let userid = this.$store.state.user.userid

    axios.get(`http://i4d102.p.ssafy.io:9000/ssac/team/myTeamList?userid=${userid}`)
      .then(response => {
        console.log(response.data)
        this.myteams = response.data

        for (let idx = 0; idx<this.myteams.length; idx++){
                  if (this.myteams[idx].logo) {
                    this.myteams[idx].logo = this.changeBlob(this.myteams[idx].logo)
                  } else {
                    this.myteams[idx].logo=''
                  }}


        // this.users = response.data.items
        // this.searched = true // 유저검색결과 한 줄 
      })
      .catch(error => {
        console.error(error)
      })
  }
}
</script>

<style>

.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
}
</style>