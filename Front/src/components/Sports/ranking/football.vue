<template>
  <div class="text-center">
    <h2 class="mb-1">Split A</h2>
    <v-data-table
      :headers="headers"
      :items="itemsA"
      class="elevation-1"
      mobile-breakpoint="0"
      hide-default-footer
    ></v-data-table>
    <br>
    <h2 class="mb-1">Split B</h2>
    <v-data-table
      :headers="headers"
      :items="itemsB"
      class="elevation-1"
      mobile-breakpoint="0"
      hide-default-footer
    ></v-data-table>

  </div>
</template>

<script>
import dataA from "./rankFootA.json"
import dataB from "./rankFootB.json"

export default {
  name:"footballranking",
  mounted() {
    this.itemsA = dataA
    this.itemsB = dataB
  },
  data() {
    return {
      headers:[
        {
          text: '팀',
          align:'end',
          sortable: false,
          value:'name'
        },
        { text: '승점', value: 'score', sortable: false,},
        { text: '승', value: 'win', sortable: false,},
        { text: '무', value: 'draw', sortable: false,},
        { text: '패', value: 'lose', sortable: false,},
        { text: '득실차', value: 'diff', sortable: false,},
      ],
      itemsA:[],
      itemsB: [
        // {
        //   name:"광주FC",
        //   score:25,
        //   득점:32,
        //   실점:46,
        //   점:46,
        //   점수:46,
        // },
        // {
        //   name:"울산현대",
        //   score:57,
        //   득점:54,
        //   실점:23,
        //   점:23,
        //   점수:23,
        // },
        // {
        //   name:"포항스틸러스",
        //   score:50,
        //   득점:56,
        //   실점:35,
        //   점:35,
        //   점수:35,
        // },
        // {
        //   name:"대구FC",
        //   score:27,
        //   득점:43,
        //   실점:39,
        //   점:39,
        //   점수:39
        // },
      ],
    }
  }

}
</script>

<style>

</style>