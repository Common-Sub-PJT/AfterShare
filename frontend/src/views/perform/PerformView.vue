<template>
<div class="container">
  <div class="wrapper">
    <div class="col cont">
      <div class="search_box">
        <div @click="sidoShow" class="dropdown">
          <div class="sido_default_option">시/도</div>  
          <ul v-if="sidoVisible">
            <li @click="sidoClick(sido)" v-for="(sido, index) in sidoList" :key="index">{{sido}}</li>
          </ul>
        </div>
        <div @click="gugunsShow" class="dropdown">
          <div class="gugun_default_option">구/군</div>  
          <ul v-if="gugunsVisible">
            <li  @click="gugunClick(gugun.gugunname)" v-for="(gugun, index) in gugunList" :key="index">{{gugun.gugunname}}</li>
          </ul>
        </div>
        <div class="search_field">
          <input @keyup.enter="searchSubmit" v-model="searchText" type="text" class="input" placeholder="공연제목">
          <button @click="searchSubmit" class="fas">Go!</button>
        </div>
      </div>
    </div>
    <div class="nav-box">
      <nav>
        <ul class="menuItems">
          <li @click="genreClick(genre)" v-for="(genre, index) in genreList" :key="index"><p>{{genre.genre}}</p></li>
        </ul>
      </nav>
    </div>
  </div>
</div>
<div class="line"></div>

<div class="container">
  <div class="row card-box">
    <div v-for="perform in this.performList.slice(0, this.performNum)" :key="perform.mt20id" class="card col-lg-4">
      <router-link :to="{ name: 'PerformDetail', params: {mt20id: perform.mt20id} }">
        <img class="card-img-top" :src='`${perform.poster}`' alt="Card image cap">
        <div class="card-body">
          <p class="card-title">{{perform.prfnm}}  [{{perform.genrenm}}]</p>
          <p class="card-date">{{perform.prfpdfrom}} ~ {{perform.prfpdto}}</p>
          <p class="card-location">{{perform.fcltynm}}</p>
        </div>
      </router-link>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi"
import { mapState } from 'pinia'
import { usePerforms } from '@/stores/performs.js'
import { defineComponent } from 'vue'

export default defineComponent ({
  name:'PerformView',
  data() {
    return {
      performList : [], 
      performNum: 20,
      isScrollEnd: false,
      sidoVisible: false,
      gugunsVisible: false,
      sidoList: [
        "서울특별시", "부산광역시", "대구광역시", 
        "인천광역시", "광주광역시", "대전광역시", "울산광역시",
        "경기도", "강원도", "충청북도", "충청남도", "전라북도",
        "전라남도", "경상북도", "경상남도", "제주특별자치도"
      ],
      gugunList : [],
      searchInfo : {
        "genre": "",
        "gugunname": "",
        "search_word": "",
        "sidoname": ""
      },
      searchText: "",
      genreList:{
        0: {code: "" , genre: "전체"},
        1: {code: "AAAA" , genre: "연극"},
        2: {code: "AAAB" , genre: "뮤지컬"},
        3: {code: "BBBA" , genre: "무용"},
        4: {code: "CCCA" , genre: "클래식"},
        5: {code: "CCCB" , genre: "오페라"},
        6: {code: "CCCC" , genre: "국악"},
        7: {code: "EEEA" , genre: "복합"},
      }
    }
  },
  watch: {
    isScrollEnd(newValue) {
      if (newValue) {
        this.performNum += 20
        this.isScrollEnd = false
      }
    }
  },
  computed: {
    ...mapState(usePerforms, ['performs'])
  },
  methods: {
    sidoShow() {
      this.sidoVisible = !this.sidoVisible
    },
    gugunsShow() {
      this.gugunsVisible = !this.gugunsVisible
    },
    fetchGuguns( sidoInfo ) {
      axios.post(secosi.performs.guguns(), sidoInfo)
        .then(res =>{
          this.gugunList = res.data
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    searchPerforms( performInfo ){
      axios.post(secosi.performs.search(), performInfo)
        .then(res => {
          this.performList = res.data
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    checkScrollEnd() {
      window.onscroll = () => {
        let bottomOfWindow = Math.max(window.pageYOffset, document.documentElement.scrollTop, document.body.scrollTop) + window.innerHeight === document.documentElement.offsetHeight

        if (bottomOfWindow) {
          this.isScrollEnd = true
        }
      }
    }
  },
  created() {
    this.performList = this.performs
  },
  mounted() {
    this.checkScrollEnd()
  },
  setup(){
    const sidoClick = function sidoClick(sido){
      this.searchInfo.gugunname = ""
      this.searchInfo.genre = ""
      this.searchInfo.sidoname = sido
      const defaultSido = document.querySelector('.sido_default_option')
      const defaultGugun = document.querySelector('.gugun_default_option')
      defaultSido.innerText = sido
      defaultGugun.innerText = "구/군"
      this.fetchGuguns(this.searchInfo)
    }
    const gugunClick = function gugunClick(gugun){
      this.searchInfo.gugunname = gugun
      const defaultGugun = document.querySelector('.gugun_default_option')
      defaultGugun.innerText = gugun
    }
    const searchSubmit = function searchSubmit() {
      this.searchInfo.search_word = this.searchText
      this.searchPerforms(this.searchInfo)
    }
    const genreClick = function genreClick(genre) {
      this.searchInfo.genre = genre.code
      this.searchPerforms(this.searchInfo)
    }
    return {
      sidoClick,
      gugunClick,
      searchSubmit,
      genreClick
    }    
  }

})
</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  outline: none;
  list-style: none;
  font-family: 'Montserrat', sans-serif;
}

.cont {
  margin: 0;
  width: 720px;
  border: 2px solid #1B3C33;
  border-radius: 5px;
}
.wrapper {
  width: 720px;
  margin-top: 5rem;
}
.wrapper .search_box{
  width: 100%;
  background: #fff;
  border-radius: 5px;
  height: 50px;
  display: flex;
  padding: 12px;
  justify-content: space-between;
  box-shadow: 0 8px 6px -10px #1e2721;
}

.wrapper .search_box .dropdown{
  width: 200px;
  border-right: 2px solid #dde2f1;
  color: #9fa3b1;
  position: relative;
  cursor: pointer;
}

.wrapper .search_box .dropdown .default_option{
  text-transform: uppercase;
  padding: 13px 15px;
  font-size: 14px;
}

.wrapper .search_box .dropdown ul{
  z-index: 10;
  overflow-y: scroll;
  height: 240px;
  position: absolute;
  top: 40px;
  left: -10px;
  background: #fff;
  width: 184px;
  border-radius: 5px;
  padding: 20px;
  display: block;
  box-shadow: 8px 8px 6px -10px #b3c6ff;
}
.wrapper .search_box .dropdown ul li{
  padding-bottom: 20px;
  z-index: 5;
}

.wrapper .search_box .dropdown ul li:last-child{
  padding-bottom: 0;
}

.wrapper .search_box .dropdown ul li:hover{
  color: #6f768d;
}

.wrapper .search_box .dropdown:before{
  content: "";
  position: absolute;
  top: 12px;
  right: 20px;
  border: 8px solid;
  border-color: #1B3C33 transparent transparent transparent;
}

/* .wrapper .search_box .search_field{
  width: 320px;
  height: 100%;
} */

.wrapper .search_box .search_field .input{
  width: 240px;
  /* height: 100%; */
  border: none;
  font-size: 16px;
  /* padding-left: 20px;
  padding-right: 10px; */
  color: #6f768d;
}

.fas{
  float: right;
  /* width: 14%; */
  padding: 3px;
  background: #1B3C33;
  color: white;
  font-size: 17px;
  border: 1px solid grey;
  border-left: none; /* Prevent double borders */
  border-radius: 15%;
  cursor: pointer;
}
.fas:hover{
  background: #326356;
}
nav {
  width: 480px;
  margin: 0px;
  /* left: 25%; */
}
nav .menuItems {
  list-style: none;
  display: flex;
  width: 480px;
}
nav .menuItems li {
  cursor: pointer;
  margin: 10px;
}
nav .menuItems li p{
  text-decoration: none;
  color: #8f8f8f;
  font-size: 15px;
  font-weight: 500;
  transition: all 0.3s ease-in-out;
  position: relative;
  text-transform: uppercase;
  z-index: 0;
}
nav .menuItems li p:hover{
  text-decoration: none;
  color: white;
  font-size: 15px;
  font-weight: bold;
  transition: all 0.3s ease-in-out;
  position: relative;
  text-transform: uppercase;
  margin: 4px;
  z-index: 0;
}
nav .menuItems li ::before{
  content: "";
  transition: 0.3s;
  background-color: #1B3C33;
  color:white;
  position: absolute;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  width: 0;
  overflow: hidden; 
  border: none;
  border-radius: 5px;
  z-index: -1;

}
nav .menuItems li :hover::before{
  width: 100%;
  transition: all 0.3s ease-in-out;
}
.container {
  display: flex;
  justify-content: center;
}
.card-box {
  display: flex;
  justify-content: center;
  margin: 0;
  justify-content: space-between;
}
.card {
  margin-bottom: 3rem;
  padding: 0;
  width: 200px;
  height: 372px;
}
.card-body {
  margin: 0;
  padding-top: 1rem;
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  vertical-align: middle;
}
.card-img-top {
  width: 200px;
  height: 264px;
}
.card-title {
  font-size: 12px;
  height: 40px;
}
.card-date {
  font-size: 10px;
  height: 20px;
}
.card-location {
  font-size: 10px;
  height: 32px;
}
li {
  list-style: none;
}
a {
  text-decoration: none;
  color: black;
}
p{
  margin: 0;
  overflow: hidden;
}
.nav-box {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
  align-items: center;
}
.line {
  width: 1024px;
  background-color: #1B3C33;
  border: 1px #1B3C33 solid;
  margin-top: 1rem;
  margin-bottom: 4rem;
}
</style>