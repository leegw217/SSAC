<template>
  <div>
    <v-list subheader>
      <br>
      <FriendsTeamResult 
        v-for="(team,idx) in fri_teams"
        :key="idx"
        :team="team"
        />
    </v-list>
  </div>
</template>

<script>
import FriendsTeamResult from './FriendsTeamResult.vue'
import axios from 'axios'
export default {
  name: "FriendsTeam", 
  components: {
    FriendsTeamResult,
  },
  data: function() {
    return {
      fri_teams: [
      // {img: 'src', name: 'team1', friend_name: '은지', people: '1'}, 
      // {img: 'src', name: 'team2', friend_name: '경연', people: '2'}, 
      // {img: 'src', name: 'team2', friend_name: '규태', people: '3'}, 
      // {img: 'src', name: 'team3', friend_name: '노정', people: '4'},
      // {img: 'src', name: 'team3', friend_name: '지원', people: '5'},
      ]
    }
  },
  computed: {
    userid: function () {
      return this.$store.state.user.email
    }
  },

  created() {
    console.log('created')
    const params = {
        params: {
          userid: this.userid,
        }
      }

    axios.get(`http://i4d102.p.ssafy.io/ssac/search/friteams/`, params)
      .then(response => {
        console.log(response.data)
        // axios 요청부분 - 요청한번보내서 this.users랑 this.teams를 다 업데이트.
        this.fri_teams = response.data
        // this.searched = response.data.searched // 유저검색결과 한 줄 
        for (let idx = 0; idx<this.fri_teams.length; idx++){
          if (this.fri_teams[idx].logo) {
            this.fri_teams[idx].logo = this.changeBlob(this.fri_teams[idx].logo)
          } else {
            this.fri_teams[idx].logo=''
          }}})
        .catch(error => {
          console.error(error)
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
      }
  }
}
</script>

<style>

</style>