<template>
  <div>
    <SearchNav style="position:fixed; z-index:100; width:100%"/>
    <br>
    <br>
    <br>
    <div style="position:static; z-index:0; width:100%;">
      <router-view />
    </div>
  </div>
</template>

<script>
import SearchNav from '@/components/Search/SearchNav.vue'
export default {
  name:"Search",
  components: {
    SearchNav,
  }
}
</script>

<style>

</style>