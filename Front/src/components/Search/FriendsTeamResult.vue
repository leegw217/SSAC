<template>
  <div>
    <hr style="height:10px; visibility:hidden;" />
  <v-card

          class="mx-auto rounded-xl"
          width="80vw"
          style="height:25vh"
        >
          <v-img
            class="white--text align-end"
            :src="team.logo"
            style="height:60%"
          >
          </v-img>
          <v-card-title
          class="pt-1 ml-2"
          style="text-align:left; font-weight: bold;">
            {{ team.name }}
          </v-card-title>

          <v-card-subtitle
          class="pt-1 ml-2" 
          style="text-align:left; font-weight: bold;">
            {{team.friend_name}}님 외에 {{ team.people }} 명이 좋아합니다.
          </v-card-subtitle>
        </v-card>
        <hr style="height:10px; visibility:hidden;" />
  </div>
</template>

<script>
export default {
  name: 'FriendsTeamResult',
  props: {
    team: {
      type: Object,
    }
  }
}
</script>

<style>

</style>