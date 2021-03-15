<template>
  <div class="fill-height">
    <!-- 로고 -->
    <v-container>
      <v-row class="mt-5 mb-5" align="center" justify="center">
        <v-img
          :src="logo.src"
          max-width="130"
          max-height="50"
          class="rounded-pill"
        >
        </v-img>
      </v-row>
    </v-container>
    <v-row class="mt-4 mb-1 ml-7" align="center">
      <h5>Login to your Account</h5>
    </v-row>
    <!-- 로그인 폼 -->
    <!-- v-model="valid" -->
  <v-form
    ref="form"
    lazy-validation
    class="mr-7 ml-7"
  >

    <v-text-field
      v-model="email"   
      :rules="emailRules"
      label="E-mail"
      required
      style="text-transform:lowercase"
      @keyup.enter="Login"
    ></v-text-field>
<!-- :rules="passwordRules" -->
    <v-text-field
      v-model="password"
      :error-messages="passErrors"
      label="password"
      required
      @keyup.enter="Login"
    ></v-text-field>

    <v-row  class="mt-3 mb-5" justify="center" align="center">
      <v-btn
        class="white--text"
        color="#536DFE"
        @click="onLogin"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
        width="265"
      >
        Login
      </v-btn>
    </v-row>
  </v-form>

  <!-- SNS 로그인 -->
  <v-container class="sns-login mb-3">
    <v-row class="mt-3 mb-5" align="center" justify="center">
      <h6>- Or Login with -</h6>
    </v-row>

    <v-row class="mb-10" align="center" justify="center">
      <!-- 카카오톡 -->
      <v-btn 
        class="mr-1 ml-1" 
        @click="KakaoLogin"
        color="#FFFFFF"
      >
        <v-img
          class="mr-1 ml-1 rounded-lg"
          :src="kakaosns.src"        
          max-height="20"
          max-width="20"
        >
        </v-img>
        <h3 class="mr-1 ml-1">KAKAO</h3>     
      </v-btn>
      <!-- 네이버 -->
      <v-btn 
        class="mr-1 ml-1" 
        @click="NaverLogin"
        color="#FFFFFF"
      >
        <v-img
          class="mr-1 ml-1 rounded-lg"
          :src="naversns.src"
          @click="NaverLogin"
          max-height="20"
          max-width="20"
        >
        </v-img>
        <h3 class="mr-1 ml-1">NAVER</h3>
      </v-btn>
    </v-row>
  </v-container>

  <div class="add-option">
    <v-row align="center" justify="center">
      <h5>Don't have an account?</h5>
      <router-link to="/signup" class="btn--text"> Sign up</router-link>
    </v-row>
  </div>

  <!-- <div class="wrap">
    <p>비밀번호를 잊으셨나요?</p>
  </div> -->
	</div>
</template>

<script>
// import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
// import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
// import GoogleLogin from "../../components/user/snsLogin/Google.vue";
// import UserApi from "../../api/UserApi";
import { login, kakaologin, naverlogin } from "@/api/user/login.js";



export default {
  name: "Login",
  computed:{
    passErrors () {
      const errors = []
      if (this.passwordSchema.validate(this.password) || (!this.pass)) {
      return errors
      } else {
        errors.push("영문,숫자 포함 8 자리이상이어야 합니다.")
      }
      return errors
    },
  },
  components: {
    // KakaoLogin,
    // GoogleLogin
  },
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password: function() {
      this.pass = true
      this.checkForm();
    },
    email: function() {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onLogin() {
      if (this.isSubmit) {
        localStorage.setItem("access-token", "");
        login(
          this.email, this.password,
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
    },
    KakaoLogin() {
      localStorage.setItem("access-token", "");
      kakaologin(
          (res) => {
            if (res.data.message === "success") {
              let token = res.data["access-token"];
              this.$store.commit("setIsLogined", true);
              localStorage.setItem("access-token", token);
  
              this.$store.dispatch("GET_KAKAO_INFO", token);
              this.$router.push("/sports");
            } else {
              this.isLoginError = true;
            }
          },
          (err) => {
            console.error(err);
            alert("아이디 또는 비밀번호가 일치하지 않습니다.");
          }        
      )
    },
    NaverLogin() {
      localStorage.setItem("access-token", "");
      naverlogin(
          (res) => {
            if (res.data.message === "success") {
              let token = res.data["access-token"];
              this.$store.commit("setIsLogined", true);
              localStorage.setItem("access-token", token);
  
              this.$store.dispatch("GET_NAVER_INFO", token);
              this.$router.push("/sports");
            } else {
              this.isLoginError = true;
            }
          },
          (err) => {
            console.error(err);
            alert("아이디 또는 비밀번호가 일치하지 않습니다.");
          }        
      )
    },
  },
  data: () => {
    return {
      pass:false,
      email: "",
      emailRules: [
        v => !!v || '이메일을 입력해주세요.',
        v => EmailValidator.validate(v) || '이메일 형식이 아닙니다.',
      ],
      passwordSchema: new PV(),
      password: "",
      passwordRules: [
        v => !!v || 'Password is required',
        v => this.checkPass(v) || 'Password must be at least 8 digits including English and number',
      ],
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this,
      logo : { src : require("@/assets/images/logo.png") },
      kakaosns : { src : require("@/assets/images/KAKAO.png") },
      naversns : { src : require("@/assets/images/NAVER.png") }
    };
  }
};
</script>
<style>
.fade-transition {
  transform: transform 0.5s ease, opacity 1000s ease;
}
</style>