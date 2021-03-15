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
          <h2 align="center" justify="center">Search</h2>
        </v-col>
        <v-col cols="3">
        </v-col>
      </v-row>
      <!-- <v-btn icon>
        <v-icon>mdi-dots-vertical</v-icon>
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
          <v-list-item @click="gotorealSearch">
            <v-list-item-title>Search</v-list-item-title>
          </v-list-item>

          <v-list-item @click="gotoFriendsTeam">
            <v-list-item-title>Following's Teams</v-list-item-title>
          </v-list-item>

          <v-list-item @click="gotoRecommend">
            <v-list-item-title>Recommend Teams</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "SearchNav",
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
    gotorealSearch() {
      if (this.$route.path !== "/tabs/search") {
        this.$router.push({name:"realSearch"})
      }
    },
    gotoFriendsTeam() {
      if (this.$route.path !== "/tabs/search/friendsteam") {
        this.$router.push({name:"FriendsTeam"})
      }
    },
    gotoRecommend() {
      if (this.$route.path !== "/tabs/search/recommend") {
        this.$router.replace({name:"Question1"})
      }
    },
  },
}
</script>

<style>

</style>