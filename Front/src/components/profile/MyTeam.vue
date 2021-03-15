<template>
  <div>
    <v-tabs
      background-color="white"
      fixed-tabs
      >
      <v-tab @click="gotoMyMyTeam">내 팀</v-tab>
      <v-tab @click="gotoNewTeam">새로운 팀</v-tab>
    </v-tabs>
    <router-view />
  </div>
</template>

<script>
export default {
  name:"MyTeam",
  methods: {
    gotoMyMyTeam() {
      if (this.$route.path !== "/profile/mymyteam") {
        this.$router.push({name:"MyMyTeam"})
      }
    },
    gotoNewTeam() {
      if (this.$route.path !== "/profile/myteam/newteam") {
        this.$router.push({name:"NewTeam"})
      }
    },
  }
}
</script>

<style>

</style>