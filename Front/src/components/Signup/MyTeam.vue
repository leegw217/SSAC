<template>
  <div class="fill-height">

    <v-row class="mt-3 ml-4 mr-4" justify="space-between">
      <v-btn icon
        @click="backtoSignup"
        color="#536DFE"
      >
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-btn icon
        @click="completeSignup"
        color="#536DFE"
      >
        <v-icon>mdi-arrow-right</v-icon>
      </v-btn>
    </v-row>

    <!-- 로고 -->
    <v-container>
      <v-row class="mt-2 mb-5" align="center" justify="center">
        <v-img
          :src="logo.src"
          max-width="130"
          max-height="50"
          class="rounded-pill"
        >
        </v-img>
      </v-row>
    </v-container>

    <v-row class="mt-4 mb-2 ml-7" align="center">
      <h5>Choose your Teams</h5>
    </v-row>

    <!-- 전체 팀 필터 -->
    <v-row class="ml-5 mr-5 mb-1" justify="space-between">
        <v-chip class="ma-2" @click="turnAll">Total</v-chip>
        <v-chip class="ma-2" @click="turnFootball" color="primary" text-color="white">Football</v-chip>
        <v-chip class="ma-2" @click="turnBaseball" color="red" text-color="white">Baseball</v-chip>
        <v-chip class="ma-2" @click="turnLOL" color="green" text-color="white">LoL</v-chip>
    </v-row>
    <!-- 전체 팀 검색-->
    <v-toolbar
      flat
      color="transparent"
      class="mt-3 ml-2 mr-2"
    >
      <v-text-field
        v-model="query"
        append-icon="mdi-magnify"
        label="Search Teams"
        single-line
      ></v-text-field>
    </v-toolbar>

    <!-- MyTeam으로 선택한 팀들 -->
    <v-row class="ml-5 mr-5 mb-1">
        <h4>My Teams:</h4>
        <v-chip
            v-for="(team, idx) in myteams"
            :key="idx"
            @click="addmyteams(team)"
            close
            small
            class="ml-1 mr-1"
        >
            {{ team.name }}
        </v-chip>
    </v-row>
    <!-- DB에 저장되어 있는 전체 팀들 -->
    <v-container class="mx-auto">
        <v-item-group
            v-model="items"
            multiple
        >
            <v-row>
                <v-col 
                v-for="(team, idx) in filterTeam(cat,query)"
                cols="4"
                :key="idx"
                :team="team"
                @click="addmyteams(team)"
                >
                <v-card
                    class="text-center"
                    height="140"
                    width="100"
                >
                    <v-avatar>
                        <img :src="team.src" alt="1">
                    </v-avatar>
                    <div>{{ team.name }}</div>
                    <div class="text--primary">{{ team.count }} likes</div>
                    <v-icon>
                        {{ isMyTeam(team) }}
                    </v-icon>
                </v-card>
                        <!-- hover -->
                        <!-- v-slot="{ active, toggle }" -->
                    <!-- <v-sheet
                        color="white"
                        elevation="2"
                        height="100"
                        rounded
                        width="100"
                    >
                            class="text-right pa-2"
                        <v-row>
                            <v-avatar 
                                @click="toggle"
                                align="center"
                                justify="center"
                            >
                                <img
                                    :src="team.src"
                                    alt="a"
                                >
                                <v-img
                                    :src="team.src"
                                    class="text-right pa-2"
                                    @click="toggle"
                                    align="center"
                                    justify="center"
                                >
                                </v-img>                  
                                <v-btn
                                    icon
                                    dark
                                >
                                    <v-icon>
                                        {{ active ? 'mdi-heart' : 'mdi-heart-outline' }}
                                    </v-icon>
                                </v-btn>
                            </v-avatar>
                        </v-row>
                        <v-row class="white--text">{{ team.name }}</v-row>
                        <v-row class="text--primary">
                        {{ team.count }} likes
                        </v-row>
                    </v-sheet> -->
                </v-col>
            </v-row>
        </v-item-group>
    </v-container>
  </div>
</template>

<script>
import { signup } from "@/api/user/signup.js"
import { getTeam } from "@/api/tabs/sports.js"
// import axios from 'axios'

export default {
    name: "MyTeam",
    data() {
        return {
            items: 
            [
                {
                    "no": Number,
                    "event_no": Number,
                    "name": "",
                    "logo": "",
                    "count": Number
                }
            ],
            cat: 0,
            query: "",
            translate: {
                1 : "축구",
                2 : "야구",
                3 : "롤"
            },
            myteams: [],
            logo : { src : require("@/assets/images/logo.png") }
        }
    },
    // filters: {
    //     sportsCategory: function (sports,cat) {
    //         const sportt = sports.filter(function (sport) {
    //             return sport.category === cat
    //         })
    //         return sportt
    //     },
    //     searchResult: function (sports,query) {
    //         const sportt = sports.filter(function (sport) {
    //             return sport.name.includes(query)
    //         })
    //         return sportt
    //     }
    // },
    methods: {
        turnAll: function() {
            this.cat = 0
        },
        turnFootball: function() {
            this.cat = 1
        },
        turnBaseball: function() {
            this.cat = 2
        },
        turnLOL: function() {
            this.cat = 3
        },
        filterTeam: function(cat,que) {
            console.log(this.items)
            return this.items.filter(function(it){
                return (it.event_no*cat === 0 || it.event_no === cat) && it.name.includes(que)
            })
        },
        addmyteams: function(item) {
            let before = this.myteams.length
            for (let index = this.myteams.length-1; index > -1; index--) {
                if (this.myteams[index] === item.no){
                    this.myteams.splice(index,1)
                }
            }
            let now = this.myteams.length
            if(before == now){
                this.myteams.push(item.no)
            }
        },
        backtoSignup() {
            this.$router.push("/signup");
        },
        completeSignup() {
            this.$store.dispatch("CREATE_USER2", this.myteams)
            console.log(this.$store.state.newUser),
            signup(
                this.$store.state.newUser,
                (res) => {
                    console.log(res.data)
                    this.$router.push("/signup/welcome")

                },
                (err) => {
                    console.log(err)
                    alert("로그인 실패하였습니다2.")
                }
            );
        },
        changeBlob(data){
        if (data.length !==0){
            const byteCharacters = window.atob(data)
            const byteNumbers = new Array(byteCharacters.length)
            for (let i = 0; i< byteCharacters.length; i++){
            byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
    
            const byteArray = new Uint8Array(byteNumbers);
            const blob = new Blob([byteArray],{type:"image/jpg"})
            const url = window.URL.createObjectURL(blob)
            
            console.log(2)
            return url
        } else {
            return ""
        }
        },
        isMyTeam(team){
            if(this.myteams.includes(team.no)){
                return 'mdi-heart' 
            } else{
                return 'mdi-heart-outline'
            }
        }
    },
    mounted: function () {
        getTeam(
            (res) => {
                this.items = res.data
                for (let idx = 0; idx<this.items.length; idx++){
                    this.items[idx].src = this.changeBlob(this.items[idx].logo)
                }
            },
            (err) => {
                console.log(err)
                // console.log(2)
            }
        )
    }
}
</script>

<style>

</style>
