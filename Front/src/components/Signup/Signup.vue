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
      <h5>Create your Account</h5>
    </v-row>

    <!-- 회원가입 폼 -->
    <v-form
      ref="form"
      lazy-validation
      class="mt-2 mr-7 ml-7 mb-10"
    >
      <!-- 닉네임 -->
      <v-text-field
        v-model="nickName"
        :error-messages="nickErrors"
        label="nickname"
        required
      ></v-text-field>
      <!-- 이메일 -->
      <v-text-field
        v-model="email"   
        :rules="emailRules"
        label="E-mail"
        required
        style="text-transform:lowercase"
      ></v-text-field>
      <!-- 비밀번호 -->
      <v-text-field
        v-model="password"
        :error-messages="passErrors"
        label="password"
        :type="passwordType"
        required
      ></v-text-field>
      <!-- 비밀번호 확인 -->
      <v-text-field
        v-model="passwordConfirm"
        :error-messages="passconfirmErrors"
        label="password-confirm"
        :type="passwordConfirmType"
        required
      ></v-text-field>
    </v-form>

    <v-row  class="mt-3 mb-3" justify="center" align="center">
      <v-btn
        class="white--text"
        color="#536DFE"
        @click="selectMyteam"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
        width="265"
      >
        NEXT
      </v-btn>
    </v-row>
  </div>
</template>

<script>
import * as EmailValidator from "email-validator";
import PV from "password-validator";
import { getUser } from "@/api/user/signup.js"


export default {
  name: "Signup",
  data: () => {
    return {
      pass:false,
      passconf:false,
      nick:false,
      email: "",
      emailRules: [
        v => !!v || '이메일을 입력해주세요.',
        v => EmailValidator.validate(v) || '이메일 형식이 아닙니다.',
      ],
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      nicknameSchema: new PV(),
      passwordRules: [
        v => !!v || 'Password is required',
        v => this.checkPass(v) || 'Password must be at least 8 digits including English and number',
      ],
      nickName: "",
      isLoading: false,
      error: {
        nickName: false,
        email: false,
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      // termPopup: false
      logo : { src : require("@/assets/images/logo.png") },
      allUsers : []
    };
  },
  created() {

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();

    this.nicknameSchema
      .is()
      .min(2)
      .is()
      .max(6);

  },

  watch: {
    password: function() {
      this.pass = true
      this.checkForm();
    },
    nickName: function() {
      this.nick = true
      this.checkForm();
    },
    email: function() {
      this.checkForm();
    },
    passwordConfirm: function() {
      this.passconf = true
      this.checkForm();
    },

  },

  methods: {
    checkForm() {
      if (this.nickName.length >= 0 && !this.nicknameSchema.validate(this.nickName))
        this.error.nickName = "닉네임 형식이 아닙니다.";
      else this.error.nickName = false;

      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >=0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문, 숫자 포함 8자리 이상이어야 합니다.";
      else this.error.password = false;

      if (this.password !== this.passwordConfirm)
        this.error.passwordConfirm = "비밀번호와 일치하지 않습니다."
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    selectMyteam() {
      if (!this.allUsers.includes(this.email)){
        if (this.isSubmit) {
          let {nickName, email, password} = this;
          console.log(nickName,email,password)
          let newuser = {
            nickName,
            email,
            password
          };
          this.$store.dispatch("CREATE_USER1", newuser);
          this.$router.push({name:"SelectTeam"});
        }
      } else {
        alert("이미 가입된 e-mail입니다.")
      }
    },
  },
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
    nickErrors () {
      const errors = []
      if (this.nicknameSchema.validate(this.nickName) || (!this.nick)) {
      return errors
      } else {
        errors.push("2글자 이상 8글자 이하이어야 합니다.")
      }
      return errors
    },
    passconfirmErrors () {
      const errors = []
      if ((this.password === this.passwordConfirm) || (!this.passconf)) {
      return errors
      } else {
        errors.push("비밀번호와 일치하지 않습니다.")
      }
      return errors
    },
  },
  mounted(){
    getUser(
      (res) => {
        console.log(res.data)
        this.allUsers = res.data
      },
      (err) => {
        console.log(err)
      }
    )
  }
};
</script>