<template>
  <div class="mt-7">
    <v-row align="center" justify="space-around">
      <v-card class="ml-6 rounded-lg deep-purple lighten-4" height="10vh" width="30vw" @click="showFootball">
        <v-img height="10vh" :src="photos[6]" style="opacity:0.8">
          <v-card-title 
        class="white--text justify-center"
        style="font-size: 15px; font-weight: bold">Football</v-card-title>
      </v-img>
      </v-card>
      <v-card  class="mx-1 rounded-lg deep-purple lighten-4" height="10vh" width="30vw" @click="showBaseball">
        <v-img height="10vh" :src="photos[9]" style="opacity:0.8">
          <v-card-title class="white--text justify-center" style="font-size: 15px; font-weight: bold">Baseball</v-card-title>
        </v-img>
      </v-card>
      <v-card  class="mr-6 rounded-lg  deep-purple lighten-4" height="10vh" width="30vw" @click="showLOL">
        <v-img height="10vh" :src="photos[8]" style="opacity:0.7">
          <v-card-title class="white--text justify-center" style="font-size: 15px; font-weight: bold">e-SPORTS</v-card-title>
        </v-img>
      </v-card>
    </v-row>

    <v-container class="mr-3" rounded color="white">
      <br>
      <div>
        <football v-if="now === 'Football'"/>
        <baseball v-else-if="now === 'Baseball'"/>
        <lol v-else-if="now === 'LOL'"/>
      </div>
    </v-container>
  </div>
</template>

<script>
import football from "./ranking/football"
import baseball from "./ranking/baseball"
import lol from "./ranking/lol"
import { mapState } from 'vuex'

export default {
  name: "SportsRanking",
  components:{
    football,
    baseball,
    lol
  },
  watch:{
    now: function () {
    }
  },
  data() {
    return {
      now:"Football",
    }
  },
  methods:{
    showFootball() {
      this.now = "Football"
    },
    showBaseball() {
      this.now = "Baseball"
    },
    showLOL() {
      this.now = "LOL"
    },
  },
  computed: {
    ...mapState(['photos'])
  }
}
</script>

<style>

</style>