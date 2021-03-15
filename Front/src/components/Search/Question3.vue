<template>
  <div class="text-center mt-4">
    <v-container>
    <h4
    style="text-align:center;
            font-weight: bold; 
            font-size: 1.7em;">3. 어떤 팀을 선호하나요?</h4>
            <br>
    <div class="mx-5 py-2"><v-btn width="80vw" :elevation="2" outlined color="black" x-large class="mt-3" @click="[saveQuestion(choices[0]), goNext()]"> {{ choices[0] }} </v-btn></div>
    <div class="mx-5 py-2"><v-btn width="80vw" :elevation="2" outlined color="black" x-large class="mt-3" @click="[saveQuestion(choices[0]), goNext()]"> {{ choices[1] }} </v-btn></div>
    <div class="mx-5 py-2"><v-btn width="80vw" :elevation="2" outlined color="black" x-large class="mt-3" @click="[saveQuestion(choices[0]), goNext()]"> {{ choices[2] }} </v-btn></div>
    </v-container>
    
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: "Question3",
  data: function() {
    return {
      choices: ['자주 이기는 상위권팀', '예측할 수 없는 중위권팀', '반전을 꿈꾸는 하위권 팀'],
      welcome : { src : require("@/assets/images/welcome1.png") }
    }
  },
  methods: {
    ...mapActions ([
      'saveQuestion',
    ]),
    goNext: function() {
      this.$router.push({ name: 'Question4'})
    }
  }
}
</script>

<style>

</style>