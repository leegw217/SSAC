<template>
  <div>
    <v-row align="center">
      <br>
      <br>
      
      <v-col>
        <div 
        v-for="(article, idx) in articles" 
        :key="idx" 
        :article="article"
        >
        <hr style="height:14px; visibility:hidden;" />
        <v-card 
        :elevation="2"
        class="mx-3 rounded-lg " 
        max-width="400" 
        outlined
        type="white"
        
>
         

          <v-card-actions>
            <v-list-item class="grow">
              <v-list-item-avatar color="grey darken-3">
                <v-img class="elevation-6" alt="" :src="profile"></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title>{{nickname}}</v-list-item-title>
              </v-list-item-content>

              <v-row align="center" justify="end">
                <v-icon class="mr-1">
                  mdi-heart
                </v-icon>
                <span class="subheading mr-2">{{like.length}}</span>
                <span class="mr-1">·</span>
                <v-icon class="mr-1">
                  mdi-email
                </v-icon>
                <span class="subheading">{{comment}}</span>
              </v-row>
            </v-list-item>
          </v-card-actions>
            <!-- <hr> -->
          <v-card-text class="headline font-weight-bold">
            {{article.content}}
          </v-card-text>

        </v-card>
        </div>
        <br>
        <br>
        <br>
      </v-col>
    </v-row>
  </div>
</template>

<script>
// import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name:"Article",
  data: function() {
    return {
      articles: [],
      userid: this.$store.state.user.userid,
      user: '',
      following: [],
      nickname: '',
      like: [],
      comment:0,
      useremail: '',
      point: '',
      follower: [],
      profile: ''
    }
  },
  computed: {
    // ...mapState({
    //  articles: 'articles',
    //  user: 'user'
  // //  })
  //   userid: function () {
  //     return this.$store.state.user.email
  //   }

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
    },
  created() {
    console.log('created')


     axios.get(`http://i4d102.p.ssafy.io/ssac/user/userSelect?userid=${this.$store.state.user.userid}`)
      .then(response => {
        console.log(response.data)
        this.nickname = response.data.usernickname
        this.useremail = response.data.userid
        this.$store.state.user.useremail = response.data.userid
        this.point = response.data.point
        this.follower = response.data.follower
        this.following = response.data.following
        this.profile = this.changeBlob(response.data.profile)
      })
      .catch(error => {
        console.error(error)
      })


    axios.get(`http://i4d102.p.ssafy.io/ssac/newsfeed/newsFeedList?userid=${this.$store.state.user.userid}`)
      .then(response => {
        console.log(response.data)
        this.articles = response.data
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
.rounded-card{
    border-radius:50px;
    width: 95%
}

</style>