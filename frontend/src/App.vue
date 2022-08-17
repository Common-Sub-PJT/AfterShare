<template>
  <div id="app">
    <header class="header">
      <router-link class="logo" to="/">
        <img style="width:auto; height:48px; padding-left:12px; padding-top:13px; margin-right: 40px;" src="@/assets/Logo.png" alt="">
      </router-link>
      <input class="menu-btn" type="checkbox" id="menu-btn" />
      <label class="menu-icon" for="menu-btn"><span class="navicon"></span></label>
      <!-- <div v-if="isLogin.isLoggedIn" class="log">{{isLogin.currentUser.name}}</div> -->
      <button v-if="isLogin.isLoggedIn" @click="[isLogin.removeToken()]" class="log main-btn">Logout</button>
      <button v-else @click="ShowLoginModal" class="log main-btn">Login</button>
      <ul class="menu">
        <li><router-link to="/conferencelist" id="r-tag">뒤풀이방</router-link></li>
        <li><router-link to="/articles" id="r-tag" @click="searchArticles('전체', '전체')">커뮤니티</router-link></li>
        <li><router-link to="/perform" id="r-tag">공연찾기</router-link></li>
        <li v-show="isLogin.isLoggedIn"><router-link :to="{ name: 'Mypage', params: { username: `${this.currentUser.name}` } }" id="r-tag">마이페이지</router-link></li>
      </ul>
    </header>
    <div id="main">
      <router-view></router-view>
    </div>
  </div>
  <login-view v-show="isLoginViewVisible" @loginClose="CloseLoginModal" @showSignup="ShowSignupModal"></login-view>
  <signup-view v-show="isSignupViewVisible" @signupClose="CloseSignupModal" @showLogin="ShowLoginModal"></signup-view>
</template>

<script>
import LoginView from '@/views/accounts/LoginView.vue'
import SignupView from '@/views/accounts/SignupView.vue'
import { useAccounts } from "@/stores/accounts"
import { useCommunities } from '@/stores/community'
import { mapActions, mapState } from 'pinia'

export default{
  name:'App',
  components: {
    LoginView,
    SignupView
  },
  data(){
    return {
      isLoginViewVisible: false,
      isSignupViewVisible: false,
    }
  },
  created() {
    this.fetchCurrentUser()
    this.searchArticles(this.genre, this.category)
  },
  setup(){
    const isLogin = useAccounts()
    return {
      isLogin
    }
  },
  computed: {
    ...mapState(useCommunities, ['genre', 'category']),
    ...mapState(useAccounts, ['currentUser'])
  },
  methods: {
    ...mapActions(useCommunities, ['searchArticles']),
    ...mapActions(useAccounts, ['fetchCurrentUser']),
    ShowLoginModal() {
      this.isLoginViewVisible = true;
    },
    CloseLoginModal() {
      this.isLoginViewVisible = false;
    },
    ShowSignupModal() {
      this.isSignupViewVisible = true;
    },
    CloseSignupModal() {
      this.isSignupViewVisible = false;
    },
  }
}
</script>
<style scoped>
/* @import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,500;1,400&display=swap'); */

#r-tag {
  color: #fff;
}
.log {
  float: right;
  cursor: pointer;
  border: 0;
  border-radius: 4px;
  font-weight: 600;
  margin: 0 15px;
  width: 100px;
  padding: 0;
  margin-top: 12px;
  box-shadow: 0 0 20px rgba(104, 85, 224, 0.2);
  transition: 0.4s;
  height: 40px;
}
.log {
  color: #1B3C33;
  background-color: rgba(255, 255, 255, 1);
  border: 1px solid ;
}
button:hover {
  color: white;
  box-shadow: 0 0 20px #fff;
  background-color: #1B3C33;
}
.header {
  background-color: #1B3C33;
  box-shadow: 1px 1px 4px 0 rgba(0,0,0,.1);
  position: sticky;
  top: 0;
  width: 100%;
  height: 64px;
  z-index: 3;
}

.header ul {
  margin: 0;
  padding: 0;
  list-style: none;
  overflow: hidden;
  background-color: #1B3C33;
}
.header li #r-tag {
  display: block;
  padding: 20px 20px;
  text-decoration: none;
}
.header li #r-tag:hover,
.header .menu-btn:hover {
  background-color: #fff;
  color: #1B3C33;
}
.header .logo {
  display: block;
  float: left;
  padding: 0px;
}

.header .menu {
  clear: both;
  max-height: 0;
  transition: max-height .2s ease-out;
}

.header .menu-icon {
  cursor: pointer;
  display: inline-block;
  float: right;
  padding: 28px 20px;
  position: relative;
  user-select: none;
}
.header .menu-icon .navicon {
  background: #fff;
  display: block;
  height: 2px;
  position: relative;
  transition: background .2s ease-out;
  width: 18px;
}
.header .menu-icon .navicon:before,
.header .menu-icon .navicon:after {
  background: #fff;
  content: '';
  display: block;
  height: 100%;
  position: absolute;
  transition: all .2s ease-out;
  width: 100%;
}
.header .menu-icon .navicon:before {
  top: 5px;
}
.header .menu-icon .navicon:after {
  top: -5px;
}

.header .menu-btn {
  display: none;
}

.header .menu-btn:checked ~ .menu {
  max-height: 240px;
}

.header .menu-btn:checked ~ .menu-icon .navicon {
  background: transparent;
}

.header .menu-btn:checked ~ .menu-icon .navicon:before {
  transform: rotate(-45deg);
}

.header .menu-btn:checked ~ .menu-icon .navicon:after {
  transform: rotate(45deg);
}

.header .menu-btn:checked ~ .menu-icon:not(.steps) .navicon:before,
.header .menu-btn:checked ~ .menu-icon:not(.steps) .navicon:after {
  top: 0;
}


@media (min-width: 820px) {
  button{
    float: right;
  }
  .header li {
    float: left;
  }
  .header li #r-tag {
    padding: 20px 30px;
  }
  .header .menu {
    clear: none;
    float: left;
    max-height: none;
  }
  .header .menu-icon {
    display: none;
  }
}
#main {
  width: 1024px;
  min-height: 1200px;
  margin: auto;
}

</style>