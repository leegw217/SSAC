<template>
  <div class="text-center mt-4">
    <v-container>
      <h3 style="text-align:center;
            font-weight: bold; 
            font-size: 1.7em;">이 팀을 추천해요</h3>
      <br>
      <hr style="height:10px; visibility:hidden;" />
      <v-card class="mx-auto rounded-xl" width="80vw" style="height:40vh">
        <v-img class="white--text align-end" :src="logo" style="height:70%">
        </v-img>
        <v-card-title class="pt-1 ml-2" style="text-align:left; font-weight: bold;">
          {{ name }}
        </v-card-title>
        <v-card-subtitle class="pt-1 ml-2" style="text-align:left; font-weight: bold;">
          {{ follownum }} 명이 좋아합니다.
        </v-card-subtitle>
      </v-card>
      <br>
  </v-container>

  <div class="mx-5 py-2">
    <v-btn outlined :elevation="2" color="black" x-large class="mt-3" @click="goNext()"> 다시하기 </v-btn>
  </div>

  </div>
</template>

<script>
import { mapActions } from 'vuex'
import axios from 'axios'

export default {
  name: "recomTeam",
  data: function() {
    return {
      name: '',
      follownum: '',
      logo: '',
    }
  },
  methods: {
    ...mapActions ([
      'saveQuestion',
    ]),
    goNext: function() {
      this.$router.push({ name: 'Question1'})
      // console.log('모든 질문에 답변 완료')
      // axios
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
  },},
  computed: {
    surveyAnswers: function () {
      return this.$store.state.surveyAnswers
    },
    userid: function () {
      return this.$store.state.user.email
    }    
    // ...mapState ([
    //   'surveyAnswers',
    //   'user.email',
    // ])
  },

  created() {
    console.log('created')
    const params = {
      params: {
        userid: this.userid,
        surveyAnswers: this.surveyAnswers[0],
      }
    }

    axios.get(`http://i4d102.p.ssafy.io/ssac/search/recommend/`, params)
      .then(response => {
        console.log(response.data)
        this.name = response.data.name
        this.follownum = response.data.count
        this.logo = this.changeBlob(response.data.logo)
        // this.searched = true // 유저검색결과 한 줄 
      })
      .catch(error => {
        console.error(error)
      })
  }
}
</script>

<style>

</style>