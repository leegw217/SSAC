<template>
  <div>
  <v-app-bar
    app
    fixed
    color="#2962FF"
    elevate-on-scroll
  >
    <h1 @click="gotoSports" class="white--text">SSAC</h1>
      
      <div class="ml-auto">
    <v-dialog
      v-model="dialog"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn icon>
          <v-icon color="white">mdi-bell-badge</v-icon>
        </v-btn>
        <v-btn 
          icon
          color="white"
          dark
          v-bind="attrs"
          v-on="on"
          ><v-icon color="white">mdi-bell-outline</v-icon>
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2">
          여러가지 알림들!
        </v-card-title>

        <v-card-text class="p-2">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </v-card-text>

      </v-card>
    </v-dialog>

        <v-btn icon @click="gotoProfile">
          <v-icon color="white">mdi-account</v-icon>
        </v-btn>
      </div>

    <template v-slot:extension>
      <v-tabs
        dark
        fixed-tabs
        >
        <v-tab @click="gotoSports">스포츠</v-tab>
        <v-tab @click="gotoFeed">피드</v-tab>
        <v-tab @click="gotoSearch">검색</v-tab>
        <v-tab @click="gotoChat">채팅</v-tab>
      </v-tabs>
    </template>



  </v-app-bar>
  </div>
</template>

<script>
export default {
  name:"Navbar",
  methods: {
    gotoSports() {
      if (this.$route.path !== "/sports") {
        this.$router.push({name:'SportsSchedule'})
      }
    },
    gotoFeed() {
      if (this.$route.path !== "/feed") {
        this.$router.push({name:'Feed'})
      }
    },
    gotoSearch() {
      if (this.$route.path !== "/search") {
        this.$router.push({name:'realSearch'})
      }
    },
    gotoChat() {
      if (this.$route.path !== "/chat") {
        this.$router.push({name:'Chat'})
      }
    },
    gotoProfile() {
      if (this.$route.path !== "/profile") {
        this.$router.push({name:'Article'})
      }
    },
  },
  data () {
      return {
        dialog: false,
      }
  },
}
</script>

<style>
.grey--text {
  color: #356859 !important;
}
</style>