import Vue from 'vue'
import VueRouter from 'vue-router'
import Start from "@/components/common/Start.vue"
import Login from "../views/Users/Login.vue";
import Signup from "../views/Users/Signup.vue";
import Profile from "../views/Users/Profile.vue";
import ProfileEdit from "../views/Users/ProfileEdit.vue";
import Tabs from "../views/Tabs/Tabs.vue";
import ChatRoom from '@/components/chat/chatRoom.vue';


Vue.use(VueRouter)

// const onlyAuthUser = async (to, from, next) => {
//   let token = localStorage.getItem("access-token");
//   if (this.$store.state.user == null && token) {
//     await this.$store.dispatch("GET_MEMBER_INFO", token);
//   }
//   if (this.$store.state.user === null) {
//     alert("로그인이 필요한 페이지입니다..");
//     // next({ name: "login" });
//     router.push({ name: "Login" });
//   } else {
//     next();
//   }
// };

const routes = [
  {
    path: "/",
    name: "Start",
    component: Start,
    children: [

    ]
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    children: [

    ]
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
    children: [
      {
        path: "/signup",
        name: "Signup1",
        component: () => import("../components/Signup/Signup.vue")
      },
      {
        path: "/signup/selectTeam",
        name: "SelectTeam",
        component: () => import("../components/Signup/MyTeam.vue")
      },
      {
        path: "/signup/welcome",
        name: "Welcome",
        component: () => import("../components/Signup/Welcome.vue")
      },
    ]
  },
  {
    path: "/profile",
    name: "Profile",
    // beforeEnter: onlyAuthUser,
    component: Profile,
    children: [
      {
        path: "/profile",
        name: "Article",
        // beforeEnter: onlyAuthUser,
        component: () => import("@/components/profile/Article.vue")
      },
      {
        path: "/profile/myteam",
        name: "MyTeam",
        // beforeEnter: onlyAuthUser,
        component: () => import("@/components/profile/MyTeam.vue"),
        children: [

          {
            path: "/profile/mymyteam",
            name: "MyMyTeam",
            // beforeEnter: onlyAuthUser,
            component: () => import("@/components/profile/MyMyTeam.vue")            
          },
          {
            path: "/profile/newteam",
            name: "NewTeam",
            // beforeEnter: onlyAuthUser,
            component: () => import("@//components/profile/NewTeam.vue"),
          }
        ]
      },
    ]
  },
  {
    path: "/tabs",
    name: "Tabs",
    // beforeEnter: onlyAuthUser,
    component: Tabs,
    children: [
      {
        path: "/tabs",
        name: "Sports",
        // beforeEnter: onlyAuthUser,
        component: () => import("../views/Tabs/Sports.vue"),
        children: [
          {
            path: "/tabs",
            name: "SportsSchedule",
            // beforeEnter: onlyAuthUser,
            component: () => import("../components/Sports/Schedule.vue")
          },
          {
            path: "/tabs/ranking",
            name: "SportsRanking",
            // beforeEnter: onlyAuthUser,
            component: () => import("../components/Sports/Ranking.vue")
          },
          {
            path: "/tabs/news",
            name: "SportsNews",
            // beforeEnter: onlyAuthUser,
            component: () => import("../components/Sports/News.vue")
          },
          {
            path: "/tabs/chat",
            name: "SportsChat",
            // beforeEnter: onlyAuthUser,
            component: () => import("../components/Sports/Chat.vue"),
            children: [
        
            ]
          },
        ]
      },
      {
        path: "/tabs/feed",
        name: "Feed",
        // beforeEnter: onlyAuthUser,
        component: () => import("../views/Tabs/Feed.vue"),
        children: [
    
        ]
      },
      {
        path: "/tabs/search",
        name: "Search",
        // beforeEnter: onlyAuthUser,
        component: () => import("../views/Tabs/Search.vue"),
        children: [
          {
            path: "/tabs/search",
            name: "realSearch",
            // beforeEnter: onlyAuthUser,
            component: () => import("@/components/Search/realSearch.vue")
          },
          {
            path: "/tabs/search/friendsteam",
            name: "FriendsTeam",
            // beforeEnter: onlyAuthUser,
            component: () => import("@/components/Search/FriendsTeam.vue")
          },
          {
            path: "/tabs/search/recommend",
            name: "Recommend",
            // beforeEnter: onlyAuthUser,
            component: () => import("@/components/Search/Recommend.vue"),
            children: [
              {
                path: "/tabs/search/recommend",
                name: "Question1",
                // beforeEnter: onlyAuthUser,
                component: () => import("@/components/Search/Question1.vue")
              },
              {
                path: "/tabs/search/recommend/question2",
                name: "Question2",
                // beforeEnter: onlyAuthUser,
                component: () => import("@/components/Search/Question2.vue")
              },
              {
                path: "/tabs/search/recommend/question3",
                name: "Question3",
                // beforeEnter: onlyAuthUser,
                component: () => import("@/components/Search/Question3.vue")
              },
              {
                path: "/tabs/search/recommend/question4",
                name: "Question4",
                // beforeEnter: onlyAuthUser,
                component: () => import("@/components/Search/Question4.vue")
              },
              {
                path: "/tabs/search/recommend/recomteam",
                name: "recomTeam",
                // beforeEnter: onlyAuthUser,
                component: () => import("@/components/Search/recomTeam.vue")
              },                           
            ]
          },
        ]
      },

    ]
  },
  {
    path: "/profile/edit",
    name: "ProfileEdit",
    // beforeEnter: onlyAuthUser,
    component: ProfileEdit,
    children: [

    ]
  },
  {
    path: '/tabs/chat-room/:id',
    name: 'ChatRoom',
    // beforeEnter: onlyAuthUser,
    component: ChatRoom,
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
