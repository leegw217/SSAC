<template>
  <div class="text-center mt-4">
    <v-container>
    <h4
    style="text-align:center;
            font-weight: bold; 
            font-size: 1.7em;">4. 어떤게 더 좋아요?</h4>
            <br>
    <div class="mx-5 py-2"><v-btn width="80vw" style="height: 15vh" :elevation="2" outlined color="black" x-large class="mt-3" @click="[saveQuestion(choices[0]), goNext()]"> {{ choices[0] }} </v-btn></div>
    <div class="mx-5 py-2"><v-btn width="80vw" style="height: 15vh" :elevation="2" outlined color="black" x-large class="mt-3" @click="[saveQuestion(choices[0]), goNext()]"> {{ choices[1] }} </v-btn></div>
    </v-container>
    
    <!-- {{ surveyAnswers }} -->
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: "Question4",
  data: function() {
    return {
      choices: ['한 명의 슈퍼스타가 이끄는 팀', '모두가 헌신하는 팀'],
      welcome : { src : require("@/assets/images/welcome1.png") }
    }
  },
  methods: {
    ...mapActions ([
      'saveQuestion',
    ]),
    goNext: function() {
      this.$router.push({ name: 'recomTeam'})
      // console.log('모든 질문에 답변 완료')
      // axios
    }
  },
  computed: {
    ...mapState ([
      'surveyAnswers',
    ])
  }
}
</script>

<style>

</style>