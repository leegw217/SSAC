<template>
<v-list-item>
  <!-- <b-list-group-item class="d-flex align-items-center">
    <b-avatar class="mr-3" src="https://mblogthumb-phinf.pstatic.net/MjAxNzAzMTVfMTY4/MDAxNDg5NTMzMTAxNTg3.3DcigjBTsc0_wKhaqCdjcRlFP4kK5J_GdUJVBZFliyIg.EVfpvEjWAGBzkOWVYiQxLlwn54CV1BJpoN9YMM9uuIkg.JPEG.doghter4our/IMG_5255.jpg?type=w800"></b-avatar>
    <span class="mr-auto">{{ team.name }}</span>
    <span class="mr-auto">{{ team.category }}  {{ team.followers }}명</span>
    <b-icon :icon="iconType" @click="[saveMyTeam(team), changeIconType()]"></b-icon>
  </b-list-group-item> -->
  
    <v-list-item-avatar>
      <v-img
        :alt="team.name"
        :src="team.logo"
      ></v-img>
    </v-list-item-avatar>

    <v-list-item-content>
      <v-list-item-title v-text="team.name"></v-list-item-title>
    </v-list-item-content>

    <v-icon @click="[saveMyTeam(team), changeIconType(team), dbRequest(team)]">{{ icon[changeIconType(team)] }}</v-icon>


</v-list-item>

</template>

<script>
import { mapActions, mapState } from 'vuex'
// import axios from 'axios'
import {addmyteam} from '@/api/tmp/search.js'

export default {
  name: 'teamSearchResult',
  props: {
    team: {
      type: Object,
    }
  },
  data() {
    return {
      icon: {
        false: 'mdi-plus-circle-outline',
        true: ''
      },
    }
  },
  methods: {
    ...mapActions ([
      'saveMyTeam',
    ]),
    changeIconType: function(team) {
      if (this.$store.state.user.myteams.includes(team.no)){
        return true
      } else {
        return false
      }
    },

    dbRequest: function(team) {
      // console.log(team)
      // console.log('created')
      // const body = {
      //   userid: this.$store.state.user.userid,
      //   team_no: team.no
      // }

      let userid = this.$store.state.user.userid
      let team_no = team.no


      addmyteam(
        userid, team_no,
        (res) => {
          console.log(res.data)
          // this.$store.state.user.myteams.push(team_no)

        },
        (err) => {
          console.log(err)
        }
      )

      // axios.post(`http://i4d102.p.ssafy.io:9000/ssac/team/myTeamInsert/`, JSON.stringify(body))
      //   .then(response => {
      //     console.log(1)
      //     console.log(response.data)
      //     console.log(2)
      //     console.log(this.$store.state.user.myteams)
      //     // axios 요청부분 - 요청한번보내서 this.users랑 this.teams를 다 업데이트.
      //     // this.fri_teams = response.data
      //     // this.searched = response.data.searched // 유저검색결과 한 줄 
      //   })
      //   .catch(error => {
      //     console.log(3)
      //     console.error(error)
      //   })

    }
  },
  computed: {
    ...mapState ([
      'myTeams',
    ])
  },
  created() {
    // console.log('created')
    if (this.team in this.myTeams) {
        this.iconType = 'mdi-minus-circle-outline'
    }
  }
}
</script>

<style>

</style>