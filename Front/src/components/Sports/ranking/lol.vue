<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="items"
      class="elevation-1"
      mobile-breakpoint="0"
      hide-default-footer
    ></v-data-table>

  </div>
</template>

<script>
import data from "./rankLOL.json"
export default {
  name:"lolranking",
  mounted() {
    this.items = data
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
        { text: '승률', value: 'rate', sortable: false,},
        { text: '승', value: 'win', sortable: false,},
        { text: '패', value: 'lose', sortable: false,},
        { text: '득실', value: 'diff', sortable: false,},
      ],
      items: [
        {
          name:"담원기아",
          winrate:0.83,
          득실차:6
        },
        {
          name:"한화생명",
          winrate:0.67,
          득실차:2
        },
        {
          name:"T1",
          winrate:0.43,
          득실차:-1
        },
        {
          name:"리브샌드박스",
          winrate:0.17,
          득실차:-6
        },
      ],
    }
  }
}
</script>

<style>

</style>