<template>
  <div>
    <SportsNav style="position:fixed; z-index:100;"/>
    <br>
    <br>
    <br>
    <div style="position:static;  z-index:0;">
      <router-view />
    </div>
  </div>
</template>

<script>
import SportsNav from '../../components/Sports/SportsNav.vue'

export default {
  name:"Sports",
  components: {
    SportsNav,
  }
}
</script>

<style>

</style> 