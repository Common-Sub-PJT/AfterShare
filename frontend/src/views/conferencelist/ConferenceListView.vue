<template>
<div class="container">
  <div class="wrapper">
    <div class="col cont">
      <div class="search-box">
        <div @click="sidoShow" class="dropdown">
          <div class="sido-default-option">시/도</div>  
          <ul v-if="sidoVisible">
            <li @click="sidoClick(sido)" v-for="(sido, index) in sidoList" :key="index">{{sido}}</li>
          </ul>
        </div>
        <div @click="gugunsShow" class="dropdown">
          <div class="gugun-default-option">구/군</div>  
          <ul v-if="gugunsVisible">
            <li  @click="gugunClick(gugun.gugunname)" v-for="(gugun, index) in gugunList" :key="index">{{gugun.gugunname}}</li>
          </ul>
        </div>
        <div class="group" style="z-index: 1;">
          <div>
            <input type="radio" name="rb" id="rb1" checked="checked"/>
            <label @click="isAfterClick(true)" for="rb1">관람자만</label>
          </div>
          <div>
            <input type="radio" name="rb" id="rb2" />
            <label @click="isAfterClick(false)" for="rb2">모두참여</label>
          </div>
        </div>
      </div>
      <div class="search-box">
        <div @click="typeShow" class="dropdown">
          <div class="type-default-option">공연 제목</div>  
          <ul v-if="typeVisible">
            <li v-if="searchInfo.is_after" @click="searchTypeClick(0)" >공연제목</li>
            <li @click="searchTypeClick(1)" >뒤풀이방 이름</li>
          </ul>
        </div>
        <div class="search-field">
          <input @keyup.enter="searchSubmit" v-model="searchText" type="text" class="input" placeholder="검색어를 입력하세요.">
          <button @click="searchSubmit" @createClose="closeCreateModal" class="fas">Go!</button>
        </div>
      </div>
    </div>
    <div class="row nav-box">
      <nav>
        <ul class="menuItems">
          <li @click="genreClick(genre)" v-for="(genre, index) in genreList" :key="index"><p>{{genre}}</p></li>
        </ul>
      </nav>
        <button v-if="isLogin.isLoggedIn" @click="showFollow" class="log main-btn">내팔로잉</button>
        <button v-if="isLogin.isLoggedIn" @click="showCreateModal" class="log main-btn">방만들기</button>
    </div>
  </div>
</div>
<div class="line"></div>

<div class="container">
  <div class="row card-row">
      <div class="card-box" v-for="conference in conferenceList" :key="conference.conference_id">
        <figure class="conf-card">
          <img v-if="conference.genrenm === genreList[1]" class="conf-img" src="@/assets/conference/1.jpg" alt="sample99" />
          <img v-else-if="conference.genrenm === genreList[2]" class="conf-img" src="@/assets/conference/2.jpg" alt="sample99" />
          <img v-else-if="conference.genrenm === genreList[3]" class="conf-img" src="@/assets/conference/3.jpg" alt="sample99" />
          <img v-else-if="conference.genrenm === genreList[4]" class="conf-img" src="@/assets/conference/4.jpg" alt="sample99" />
          <img v-else-if="conference.genrenm === genreList[5]" class="conf-img" src="@/assets/conference/5.jpg" alt="sample99" />
          <img v-else-if="conference.genrenm === genreList[6]" class="conf-img" src="@/assets/conference/6.jpg" alt="sample99" />
          <img v-else-if="conference.genrenm === genreList[7]" class="conf-img" src="@/assets/conference/7.jpg" alt="sample99" />
          <img v-else class="conf-img" src="@/assets/conference/8.jpg" alt="sample99" />
          <img v-if="conference._secret" src="@/assets/conference/padlock.png" alt="" class="lock-img">
          <p class="limit">{{conference.person_now}}/{{conference.person_limit}}</p>
          <figcaption>
            <h3 v-if="conference._after">관람자만</h3>
            <h3 v-else>모두참여</h3>
            <div v-if="(conference.person_now === conference.person_limit) && isLogin.isLoggedIn"><button @click="cantAlert" class="conf-in">입장하기</button></div>
            <div v-else-if="conference._secret && isLogin.isLoggedIn"><button @click="secretAlert(conference.password, conference.conference_id, conference.title)" class="conf-in">입장하기</button></div>
            <div v-else-if="isLogin.isLoggedIn"><button @click="basicAlert(conference.conference_id, conference.title)" class="conf-in">입장하기</button></div>
          </figcaption>
        </figure>
        <div class="text-box">
          <p class="card-text">방 제목: {{conference.title}}</p>
          <p class="card-text">공연이름: {{conference.prfnm}}</p>
          <p class="card-text">공연일시: {{conference.perform_day}}</p>
        </div>
      </div>
  </div>
</div>
<conference-create v-show="isRoomCreateVisible" @createClose="closeCreateModal"></conference-create>
</template>

<script>
import { defineComponent } from "vue"
import { mapState } from 'pinia'
import { useAccounts } from "@/stores/accounts";
import secosi from "@/api/secosi"
import axios from "axios"
import Swal from 'sweetalert2'
import ConferenceCreate from '@/views/conferencecreate/ConferenceCreateView.vue'
import router from '@/router'

export default defineComponent ({
  name: 'ConferenceListView',
  components:{ 
    ConferenceCreate,
  },
  data() {
    return {
      isRoomCreateVisible: false,
      conferenceList : [], 
      sidoVisible: false,
      gugunsVisible: false,
      typeVisible: false,
      sidoList: [
        "서울특별시", "부산광역시", "대구광역시", 
        "인천광역시", "광주광역시", "대전광역시", "울산광역시",
        "경기도", "강원도", "충청북도", "충청남도", "전라북도",
        "전라남도", "경상북도", "경상남도", "제주특별자치도"
      ],
      gugunList : [],
      gugunSearchInfo : {
        "genre": "",
        "gugunname": "",
        "search_word": "",
        "sidoname": ""
      },
      searchInfo: {
        "genrenm": "ALL",
        "is_after": true,
        "keyword": "",
        "sido": "",
        "sigungu": "",
        "type": 0
      },
      searchText: "",
      genreList:[
        "ALL", "뮤지컬", "연극", "무용", "클래식", "오페라", "국악", "복합"
      ],
      searchTypes:["공연명", "뒤풀이방 제목"],
    }
  },
  methods:{
    showCreateModal() {
      this.isRoomCreateVisible = true
    },
    closeCreateModal() {
      this.isRoomCreateVisible = false
    },
    sidoShow() {
      this.sidoVisible = !this.sidoVisible
    },
    gugunsShow() {
      this.gugunsVisible = !this.gugunsVisible
    },
    typeShow(){
      this.typeVisible = !this.typeVisible
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
    searchConferences( searchInfo ){
      axios.post(secosi.conferences.search(), searchInfo)
        .then(res => {
          this.conferenceList = res.data
          console.log(res.data);
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    fetchConferences() {
      axios.get(secosi.conferences.conferences())
        .then(res => {
          this.conferenceList = res.data
        })
    },
    followConferences() {
      const context = this.currentUser.userId

      axios.get(secosi.conferences.follow()+`/${context}`)
        .then(res => {
          this.conferenceList = res.data
        })
    },
    basicAlert(id, title) {
      Swal.fire({
        title: '입장하시겠습니까?',
        confirmButtonColor: '#1b3c33',
        confirmButtonText: '입장하기',
        showCloseButton: true,
      }).then(function(){
        router.push({ name: 'ConferenceDetail', params:{ conferenceid: id, title: title }  })
      })
    },
    cantAlert() {
      Swal.fire({
        showConfirmButton: false,
        title: '정원이 다 차 입장할 수 없습니다.',
        showCancelButton: true,
        cancelButtonText: '돌아가기',
      })
    },
    secretAlert(password, id, title) {
      Swal.fire({
        title: '입장하시겠습니까?',
        input: 'password',
        inputLabel: '비밀번호',
        inputPlaceholder: '비밀번호를 입력해주세요.',
        showCloseButton: true,
        confirmButtonColor: '#1b3c33',
        confirmButtonText: '입장하기',
        inputAttributes: {
          autocapitalize: 'off',
          autocorrect: 'off'
        },
        inputValidator: (inputPassword) => {
          if (inputPassword != password) {
            return '잘못된 비밀번호입니다.'
          }else{
            router.push({ name: 'ConferenceDetail', params:{ conferenceid: id, title: title }  })
          }
        },
      })
    },
  },
  created() {
    this.fetchConferences()
  },
  computed: {
    ...mapState(useAccounts, ['currentUser'])
  },
  setup() {
    const sidoClick = function sidoClick(sido){
      this.gugunSearchInfo.gugunname = ""
      this.gugunSearchInfo.genre = ""
      this.gugunSearchInfo.sidoname = sido
      
      this.searchInfo.genrenm= "ALL"
      this.searchInfo.sigungu = ""
      this.searchInfo.sido = sido

      const defaultSido = document.querySelector('.sido-default-option')
      const defaultGugun = document.querySelector('.gugun-default-option')
      defaultSido.innerText = sido
      defaultGugun.innerText = "구/군"
      this.fetchGuguns(this.gugunSearchInfo)
    }
    const gugunClick = function gugunClick(gugun){
      this.searchInfo.sigungu = gugun
      const defaultGugun = document.querySelector('.gugun-default-option')
      defaultGugun.innerText = gugun
    }
    const searchSubmit = function searchSubmit() {
      this.searchInfo.genrenm= "ALL"
      this.searchInfo.search_word = this.searchText
      this.searchConferences(this.searchInfo)
    }
    const genreClick = function genreClick(genre) {
      this.searchInfo.genrenm = genre
      this.searchConferences(this.searchInfo)
    }
    const searchTypeClick = function searchTypeClick(type) {
      this.searchInfo.type = type
      const defaultType = document.querySelector('.type-default-option')
      if (type === 0) {
        defaultType.innerText = "공연제목"
      }else{
        defaultType.innerText = "뒤풀이방"
      }
    }
    const isAfterClick = function isAfterClick(isAfter) {
      if (isAfter) {
        this.searchInfo.type = 0
      }else{
        this.searchInfo.type = 1
        const defaultType = document.querySelector('.type-default-option')
        defaultType.innerText = "뒤풀이방"
      }
      this.searchInfo.is_after = isAfter
    }
    const showFollow = function showFollow() {
      this.followConferences()
    }
    const isLogin = useAccounts()
    return {
      sidoClick,
      gugunClick,
      searchSubmit,
      genreClick,
      searchTypeClick,
      isAfterClick,
      showFollow,
      isLogin,
    }
  },
})
</script>

<style scoped>
 @import url(https://fonts.googleapis.com/css?family=Montserrat:200);
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  outline: none;
  list-style: none;
  font-family: 'Montserrat', sans-serif;
}
.card-row {
  display: flex;
  justify-content: space-between;
}

.nav-box {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
  align-items: center;
}
.main-btn {
  cursor: pointer;
  border-radius: 5px;
  margin-left: 1rem;
  width: 80px;
  height: 32px;
  padding: 0;
  transition: 0.4s;
  color: #1B3C33;
  background-color: #fafafa;
  border: 1px solid ;
}
.main-btn:hover {
  color: white;
  background-color: #1B3C33;
}
.container {
  display: flex;
  justify-content: center;
}
.line {
  width: 1024px;
  background-color: #1B3C33;
  border: 1px #1B3C33 solid;
  margin-top: 1rem;
  margin-bottom: 4rem;
}
.wrapper {
  width: 720px;
  margin-top: 5rem;
}
.wrapper .search-box{
  width: 720px;
  border-radius: 5px;
  height: 40px;
  display: flex;
  justify-content: space-between;
  padding: 10px;
  box-shadow: 0 8px 6px -10px #1e2721;
}

.wrapper .search-box .dropdown{
  width: 200px;
  border-right: 2px solid #dde2f1;
  color: #9fa3b1;
  cursor: pointer;
}

.wrapper .search-box .dropdown .default-option{
  text-transform: uppercase;
  padding: 13px 15px;
  font-size: 14px;
}

.wrapper .search-box .dropdown ul{
  overflow-y: scroll;
  height: 200px;
  top: 35px;
  left: -10px;
  background: #fff;
  width: 184px;
  border-radius: 5px;
  padding: 20px;
  display: block;
  box-shadow: 8px 8px 6px -10px #b3c6ff;
  position: absolute;
  z-index: 1;
}
.wrapper .search-box .dropdown ul li{
  padding-bottom: 20px;
  z-index: 1;
}

.wrapper .search-box .dropdown ul li:last-child{
  padding-bottom: 0;
}

.wrapper .search-box .dropdown ul li:hover{
  color: #6f768d;
}

.wrapper .search-box .dropdown:before{
  content: "";
  position: absolute;
  top: 10px;
  right: 20px;
  border: 8px solid;
  border-color: #1B3C33 transparent transparent transparent;
}

.wrapper .search-box .search-field{
  width: 480px;
  height: 100%;
  position: relative;
}

.wrapper .search-box .search-field .input{
  width: 440px;
  height: 100%;
  border: 0px;
  font-size: 16px;
  /* padding-left: 20px;
  padding-right: 10px; */
  color: #6f768d;
}

.wrapper .search-box .search-field .fas{
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
.wrapper .search-box .search-field .fas:hover{
  background: #326356;
}
.cont {
  margin: 0;
  width: 720px;
  border: 2px solid #1B3C33;
  border-radius: 5px;
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
.conference-list{
  position: relative;
  top: 120px;
}

.group {
  display: flex;
  align-items: center;
  margin-bottom: 0px;
  margin-left: 6px;
  width: 240px;
}
.group input[type="checkbox"],
.group input[type="radio"] {
  position: absolute;
  opacity: 0;
}
label {
  position: relative;
  margin-right: 1rem;
  padding-left: 1.2em;
  padding-right: 1.2em;
  line-height: 2;
  cursor: pointer;
}
label::before{
  box-sizing: border-box;
  content: " ";
  position: absolute;
  top: 0.3em;
  left: 0;
  display: block;
  width: 1em;
  height: 1.4em;
  border: 2px solid grey;
  border-radius: .25em;
  z-index: -1;
}
.group input[type="radio"] + label::before {
  border-radius: 1em;
}
.group input[type="checkbox"]:checked + label,
.group input[type="radio"]:checked + label {
  padding-left: 1em;
  color: white;
}
.group input[type="checkbox"]:checked + label::before,
.group input[type="radio"]:checked + label::before {
    top: 0;
    width: 100%;
    height: 2em;
    background: #1B3C33;
}
label,
label::before {
  -webkit-transition: .25s all ease;
  -o-transition: .25s all ease;
  transition: .25s all ease;
}

.conf-card {
  font-family: 'Montserrat', Arial, sans-serif;
  position: relative;
  display: inline-block;
  overflow: hidden;
  margin: 0;
  width: 240px;
  /* background-color: #ffffff; */
  color: #1B3C33;
  text-align: left;
  font-size: 16px;
  /* border-radius: 5px 5px 0 0; */
  border-bottom: 1px solid black;
}
.conf-card *,
.conf-card:before,
.conf-card:after {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all 0.4s ease;
  transition: all 0.4s ease;
}
.conf-card img {
  max-width: 99%;
  border-radius: 5px;
  backface-visibility: hidden;
  vertical-align: top;
}
.conf-card:before,
.conf-card:after {
  content: '';
  background-color: #fff;
  opacity: 0;
}
.conf-card:before {
  width: 0;
  height: 1px;
}
.conf-card:after {
  height: 0;
  width: 1px;
}
.conf-card figcaption {
  position: absolute;
  left: 25%;
  top: 25%;
  padding: 15px 20px;
}
.conf-card h3,
.conf-card div {
  margin: 0;
  font-size: 1rem;
  font-weight: normal;
  opacity: 0;
}
.conf-card a {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.conf-card:hover img,
.conf-card.hover img {
  zoom: 1;
  filter: alpha(opacity=20);
  -webkit-opacity: 0.2;
  opacity: 0.2;
}
.conf-card:hover:before,
.conf-card.hover:before,
.conf-card:hover:after,
.conf-card.hover:after {
  opacity: 1;
  -webkit-transition-delay: 0.25s;
  transition-delay: 0.25s;
}
.conf-card:hover:before,
.conf-card.hover:before {
  width: 40px;
}
.conf-card:hover:after,
.conf-card.hover:after {
  height: 40px;
}
.conf-card:hover h3,
.conf-card.hover h3,
.conf-card:hover div,
.conf-card.hover div {
  opacity: 1;
}
.conf-card:hover h3,
.conf-card.hover h3
.conf-card:hover div,
.conf-card.hover div {
  -webkit-transition-delay: 0.3s;
  transition-delay: 0.3s;
}
.conf-img {
  width: 100%;
  height: 136px;
  /* background-color: #ffffff; */
}
.conf-in {
  background-color: #1B3C33;
  color: #ffffff;
  border: none;
  border-radius: 5px;
}
.card-col {
  /* column-count: 4; */
  width: 1024px;
}
.card-box {
  width: 240px;
  margin: 0 0 2rem 0;
  border: 1px solid black;
  border-radius: 5px;
}
.text-box {
  height: 72px;
  margin: 0.5rem 1rem 0;
}
.card-text {
  overflow: hidden;
  height: 20px;
  font-size: 12px;
}
.lock-img {
  position: absolute;
  right: 0;
  width: 24px;
  height: 24px;
  margin: 0.5rem;
}
.limit {
  position: absolute;
  bottom: 0;
  right: 0;
  margin: 0.5rem;
}
</style>