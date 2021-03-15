<template>
<div style="width:100%;">
  <!-- Systembar & Navbar -->
  <v-system-bar color="deep-purple darken-3"></v-system-bar>
  <v-app-bar
    color="deep-purple accent-4"
    dark
    height="54px"
  >
    <v-row>
      <v-col cols="3">
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      </v-col>
      <v-col class="mt-1" cols="6">
        <h2 align="center" justify="center">Sports</h2>
      </v-col>
      <v-col cols="3">

      </v-col>
    </v-row>
    <!-- <v-btn icon>
      <v-icon>mdi-dots-vertical</v-icon>
    </v-btn>
    <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-filter</v-icon>
    </v-btn> -->
  </v-app-bar>

  <v-navigation-drawer
    v-if="drawer"
    v-model="drawer"
    temporary
    bottom
  >
    <v-list
      nav
      dense
    >
      <v-list-item-group
        v-model="group"
        active-class="deep-purple--text text--accent-4"
      >
        <v-list-item @click="gotoSchedule">
          <v-list-item-title>Schedule</v-list-item-title>
        </v-list-item>

        <v-list-item @click="gotoRanking">
          <v-list-item-title>Ranking</v-list-item-title>
        </v-list-item>

        <v-list-item @click="gotoNews">
          <v-list-item-title>News</v-list-item-title>
        </v-list-item>

        <v-list-item @click="gotoChat">
          <v-list-item-title>Chat</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-navigation-drawer>
</div>
</template>

<script>
export default {
  name:"SportsNav",
  data: () => ({
    drawer: false,
    group: null,
  }),
  watch: {
    group () {
      this.drawer = false
    },
  },
  methods: {
    gotoSchedule() {
      if (this.$route.path !== "/tabs") {
        this.$router.push({name:"SportsSchedule"})
      }
    },
    gotoRanking() {
      if (this.$route.path !== "/tabs/ranking") {
        this.$router.push({name:"SportsRanking"})
      }
    },
    gotoNews() {
      if (this.$route.path !== "/tabs/news") {
        this.$router.replace({name:"SportsNews"})
      }
    },
    gotoChat() {
      if (this.$route.path !== "/tabs/chat") {
        this.$router.replace({name:"SportsChat"})
      }
    },
  }
}
</script>

<style>

</style>