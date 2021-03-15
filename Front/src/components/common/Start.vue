<template>
  <div class="deep-purple accent-4 fill-height fill-width">
    <v-container class="fill-height" align="center" justify="center">
      <v-row align="center" justify="center">
        <v-btn
          depressed
          color="deep-purple accent-4"
          @click="gotoLogin"
        >
          <h1 class="white--text">SSAC</h1> 
        </v-btn>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      start : { src : require("@/assets/images/START.png") }
    }
  },
  methods: {
    gotoLogin() {
      this.$router.push("/login");
    }        
  },
  mounted() {
    const targets = this.$el;
    this
      .$anime
      .timeline()
      .add({
        targets,
        easing: 'easeOutExpo',
      });
  },
}
</script>

<style scoped>
</style>