<template>
  <v-list-item @click="gotoInF">
    <v-list-item-avatar>
      <v-img
        :alt="user.name"
        src="https://mblogthumb-phinf.pstatic.net/MjAxNzAzMTVfMTg0/MDAxNDg5NTMzMTAwNjQ0.V7tqJArWLv350swvN0w8KFfjGEE8LFauuomjhAjJv9Eg.ARhfQCD6kfix4RdJt5YK4EYdvW6HIT2ctqkPlw6RRgsg.JPEG.doghter4our/IMG_5221.jpg?type=w800"
      ></v-img>
    </v-list-item-avatar>

    <v-list-item-content>
      <v-list-item-title v-text="user.nickname"></v-list-item-title>
    </v-list-item-content>

  </v-list-item> 
</template>

<script>
export default {
  name: 'userSearchResult',
  props: {
    user: {
      type: Object,
    }
  },
  methods :{
    gotoInF(){
      this.$router.push({name:'Article'})
    }
  }
}
</script>

<style>

</style>