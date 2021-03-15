<template>
  <div class="fill-height">
    <v-container>
      <v-row class="mt-10 mb-5" align="center" justify="center">
        <v-img
          :src="welcome.src"
          max-width="250"
          max-height="200"
          class="rounded-pill"
        >
        </v-img>
      </v-row>
    </v-container>

    <v-row class="mt-5 mb-2" align="center" justify="center">
      <h1>Hello!</h1>
    </v-row>
    <v-row class="mt-2 mb-5" align="center" justify="center">
      <h5 align="center" justify="center">
        Welcome to SSAC!<br> 
        SSAC is a Sports SNS as Calendar<br> 
        SSAC will help you enjoy your life<br>
        with COVID-19<br>
      </h5>
    </v-row>

    <v-row  class="mt-10" justify="center" align="center">
      <v-btn
        class="white--text"
        color="#536DFE"
        @click="startSSAC"
        width="265"
      >
        Let's Start!
      </v-btn>
    </v-row>
  </div>
</template>

<script>
import {login} from "../../api/user/login.js"

export default {
  name: "Welcome",
  data() {
    return {
      welcome : { src : require("@/assets/images/welcome2.png") }
    }
  },
  methods: {
    startSSAC() {
      localStorage.setItem("access-token", "");
      let email = this.$store.state.newUser.email 
      let password = this.$store.state.newUser.password 
      login(
        email, password,
        (res) => {
          if (res.data.message === "success") {
            let token = res.data["access-token"];
            this.$store.commit("setIsLogined", true);
            localStorage.setItem("access-token", token);

            this.$store.dispatch("GET_MEMBER_INFO", res.data);
            this.$router.push({name:"SportsSchedule"});
          } else {
            this.isLoginError = true;
          }
        },
        (err) => {
          console.error(err);
          alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
      )
    }
  }
}
</script>

<style>

</style>