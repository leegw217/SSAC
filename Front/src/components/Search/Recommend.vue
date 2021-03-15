<template>
  <div>
    <!-- <Question1 v-if="questionNum === 1" /> -->
    <router-view />
  </div>
</template>

<script>
// import Question1 from '@/components/Search/Question1.vue'
import { mapState } from 'vuex'
export default {
  name: "Recommend",
  components: {
    // Question1,
  },
  data: function() {
    return {
      // questionNum: 1
    }
  },
  methods: {
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