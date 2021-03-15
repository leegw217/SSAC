<template>
  <div class="">
    <div class="p-3">
      <v-row v-for="(post, idx) in posts" :key="idx">
        <v-col cols=12>
          <a :href="post.link" target="_Blank">
          <v-card
            class="ml-3 rounded-xl"
            max-width="335"
          >
              <v-img 
                :src="post.imgsrc"
              ></v-img>
            <v-card-title>
              {{ post.title }}
            </v-card-title>
          </v-card>
        </a>
        </v-col>
      </v-row>
      <br>
      <br>
      <br>
    </div>
  </div>
</template>

<script>
import data from "./newsBase.json"

export default {
  name: "NewsBase",
  mounted() {
    this.posts = data
  },
  data() {
    return { 
      posts:[
        // {
        //   imgsrc:"https://placekitten.com/70/70",
        //   headline:"headline1",
        //   content:"content1"
        // },
        // {
        //   imgsrc:"",
        //   headline:"headline2",
        //   content:"content2"
        // },
        // {
        //   imgsrc:"",
        //   headline:"headline3",
        //   content:"content3"
        // },
        // {
        //   imgsrc:"",
        //   headline:"headline4",
        //   content:"content4"
        // },
      ]
    }
  }
}
</script>

<style>
a {
  text-decoration: none;
}
</style>