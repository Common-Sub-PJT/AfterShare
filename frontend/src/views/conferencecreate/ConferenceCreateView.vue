<template>
  <div class="modal" id="email-modal">
    <div class="modal-content">
      <div class="modal-content-left">
        <img id="modal-img" src="@/assets/conferenceCreate.png" alt="모달이미지">
      </div>
      <div class="modal-content-right">
        <div @click="$emit('createClose')" class="close-btn">&times;</div>
        <form class="modal-form" id="form">
          <h1 style="font-weight: border;">뒤풀이방 생성</h1>
          <div class="wrapper">
            <div class="search-box">
              <div class="search-field">
                <input v-model="nameText" type="text" class="input" placeholder="생성할 방 이름을 입력하세요" required="required">
              </div>
            </div>
            <div class="search-box">
              <div class="group" style="z-index: 2; margin-top: 2px;">
                <div>
                  <input type="radio" name="rb" id="rb3" checked="checked"/>
                  <label @click="isAfterClickCreate(true)" for="rb3">관람자만</label>
                </div>
                <div>
                  <input type="radio" name="rb" id="rb4" />
                  <label @click="isAfterClickCreate(false)" for="rb4">모두참여</label>
                </div>
              </div>
              <div @click="headCountShow" class="dropdown" style="width:120px; margin-left: 20px;">
                <div class="create-head-default">제한인원 : 5</div>  
                <ul v-if="headCountVisible">
                  <li @click="headCountClick(headcount)" v-for="(headcount, index) in headcountList" :key="index">{{headcount}}</li>
                </ul>
              </div>
            </div>
            <div class="search-box">
              <div class="group" style="z-index: 2; margin-top: 2px;">
                <div>
                  <input type="radio" name="rbc" id="rb5" checked="checked"/>
                  <label @click="isLockClick(false)" for="rb5">공개</label>
                </div>
                <div>
                  <input type="radio" name="rbc" id="rb6" />
                  <label @click="isLockClick(true)" for="rb6">잠금</label>
                </div>
                <div v-if="passwordVisible" class="search-field" style="width:200px;">
                  <input v-model="passwordGet" type="password" class="input" placeholder="비밀번호 입력" required="required">
                </div>
                <div v-else>
                  <div style="width:200; background-color: aquamarine; z-index: 50000"></div>
                </div>
              </div>          
            </div>
            <div class="search-box" v-if="performSearchVisible">
              <div @click="sidoShow" class="dropdown" style="margin:0px; width: 240px;">
                <div class="create-sido-default">시/도</div>  
                <ul v-if="sidoVisible">
                  <li @click="sidoClick(sido)" v-for="(sido, index) in sidoList" :key="index">{{sido}}</li>
                </ul>
              </div>
              <div @click="gugunsShow" class="dropdown" style="margin:0px">
                <div class="create-gugun-default">구/군</div>  
                <ul v-if="gugunsVisible">
                  <li @click="gugunClickCreate(gugun.gugunname)" v-for="(gugun, index) in gugunList" :key="index">{{gugun.gugunname}}</li>
                </ul>
              </div>
              <div @click="genreShow" class="dropdown" style="margin:0px">
                <div class="create-genre-default">장르</div>  
                <ul v-if="genreVisible">
                  <li @click="genreClick(genre)" v-for="(genre, index) in genreList" :key="index">{{genre.genre}}</li>
                </ul>
              </div>
            </div>
            <div class="search-box" style="margin-bottom:16px;" v-if="performSearchVisible">
              <div class="search-field">
                <input @keyup.enter="searchSubmit" v-model="searchText" type="text" class="input" placeholder="공연 제목 Search">
                <button type="button" @click="searchSubmit" class="fas">Go!</button>
              </div>
            </div>
            <div class="search-box" v-if="performSearchVisible">
              <div @click="performShow" class="dropdown" style="width:400px;">
                <div class="create-perform-default">검색 후 공연을 선택해주세요.</div>  
                <ul v-if="performVisible" style="width:400px;">
                  <li @click="performClick(perform)" v-for="(perform, index) in performList" :key="index">{{perform.prfnm}}</li>
                </ul>
              </div>
            </div>
            <div class="search-box" v-if="performSearchVisible">
              <div @click="calShow" style="width:250px;">
                <div class="create-day-default" style="color: #b1b1b1;">공연날짜를 선택하세요.</div>  
                <DatePicker v-if="calVisible" @click="changeDate(getDate)" v-model="getDate"/>
              </div>
              <div @click="timeShow" class="dropdown" style="margin:0px; width:120px">
                <div class="create-time-default">공연시간</div>  
                <ul v-if="timeVisible">
                  <li @click="timeClick(time)" v-for="(time, index) in timeList" :key="index">{{time.time}}</li>
                </ul>
              </div>
            </div>
          </div>
          <input type="button" @click="conferenceCreate" class="modal-input-btn" value="Create">
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";
import secosi from "@/api/secosi"
import axios from "axios"
import { Calendar, DatePicker } from 'v-calendar'
import { useAccounts } from "@/stores/accounts";
import { mapState } from 'pinia'
import router from '@/router'


export default defineComponent ({
  name:'ConferenceCreate',
  components: {
    Calendar,
    DatePicker
  },
  computed: {
    ...mapState(useAccounts, ['currentUser'])
  },
  data() {
    return {
      getDate: new Date(),
      date:"",
      calVisible: false,
      sidoVisible: false,
      gugunsVisible: false,
      headCountVisible: false,
      genreVisible: false,
      typeVisible: false,
      performSearchVisible: true,
      performVisible: false,
      passwordVisible : false,
      passwordGet : "",
      timeVisible: false,
      timeList:{
        0: {code: "9시 00분" , time: "AM 9:00"},
        1: {code: "9시 30분" , time: "AM 9:30"},
        2: {code: "10시 00분" , time: "AM 10:00"},
        3: {code: "10시 30분" , time: "AM 10:30"},
        4: {code: "11시 00분" , time: "AM 11:00"},
        5: {code: "11시 30분" , time: "AM 11:30"},
        6: {code: "12시 00분" , time: "PM 12:00"},
        7: {code: "12시 30분" , time: "PM 12:30"},
        8: {code: "13시 00분" , time: "PM 1:00"},
        9: {code: "13시 30분" , time: "PM 1:30"},
        10: {code: "14시 00분" , time: "PM 2:00"},
        11: {code: "14시 30분" , time: "PM 2:30"},
        12: {code: "15시 00분" , time: "PM 3:00"},
        13: {code: "15시 30분" , time: "PM 3:30"},
        14: {code: "16시 00분" , time: "PM 4:00"},
        15: {code: "16시 30분" , time: "PM 4:30"},
        16: {code: "17시 00분" , time: "PM 5:00"},
        17: {code: "17시 30분" , time: "PM 5:30"},
        18: {code: "18시 00분" , time: "PM 6:00"},
        19: {code: "18시 30분" , time: "PM 6:30"},
        20: {code: "19시 00분" , time: "PM 7:00"},
        21: {code: "19시 30분" , time: "PM 7:30"},
        22: {code: "20시 00분" , time: "PM 8:00"},
        23: {code: "20시 30분" , time: "PM 8:30"},
        24: {code: "21시 00분" , time: "PM 9:00"},
        25: {code: "21시 30분" , time: "PM 9:30"},
        26: {code: "22시 00분" , time: "PM 10:00"},
        27: {code: "22시 30분" , time: "PM 10:30"},
        28: {code: "23시 00분" , time: "PM 11:00"},
        29: {code: "23시 30분" , time: "PM 11:30"},
      },
      headcountList: [ 2, 3, 4, 5, 6, 7, 8 ],
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
      performSearchInfo : {
        "genre": "",
        "gugunname": "",
        "search_word": "",
        "sidoname": ""
      },
      createInfo: {
        "_after": true,
        "_secret": false,
        "_spoiler": true,
        "genrenm": "",
        "mt20id": "",
        "owner_id": "",
        "password": "",
        "perform_day": "",
        "person_limit": 5,
        "prfnm": "",
        "sido": "",
        "sigungu": "",
        "title": ""
      },
      searchText: "",
      nameText: "",
      genreList:{
        0: {code: "" , genre: "전체"},
        1: {code: "AAAA" , genre: "연극"},
        2: {code: "AAAB" , genre: "뮤지컬"},
        3: {code: "BBBA" , genre: "무용"},
        4: {code: "CCCA" , genre: "클래식"},
        5: {code: "CCCB" , genre: "오페라"},
        6: {code: "CCCC" , genre: "국악"},
        7: {code: "EEEA" , genre: "복합"},
      },
      searchTypes:["공연명", "뒤풀이방 제목"],
      performList:[],
      conrerenceId: "",
    }
  },
  methods:{
    showCreateModal() {
      this.isRoomCreateVisible = true
    },
    sidoShow() {
      this.sidoVisible = !this.sidoVisible
    },
    gugunsShow() {
      this.gugunsVisible = !this.gugunsVisible
    },
    headCountShow() {
      this.headCountVisible = !this.headCountVisible
    },
    genreShow(){
      this.genreVisible = ! this.genreVisible
    },
    typeShow(){
      this.typeVisible = !this.typeVisible
    },
    performShow(){
      this.performVisible = !this.performVisible
    },
    calShow(){
      this.calVisible = !this.calVisible
    },
    timeShow(){
      this.timeVisible = !this.timeVisible
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
    fetchConferences( searchInfo ){
      axios.post(secosi.conferences.search(), searchInfo)
        .then(res => {
          this.conferenceList = res.data
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    createRoom( roomInfo ){
      axios.post(secosi.conferences.create(), roomInfo,
        { headers: {
          Authorization : `Bearer ${ localStorage.getItem('token') }`
        }}
      )
        .then(res => {
          console.log(res.data)
          router.push({ name: 'ConferenceDetail', params:{ conferenceid: res.data, userId: this.currentUser.userId }  })
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
  },
  setup() {
    const sidoClick = function sidoClick(sido){
      this.gugunSearchInfo.gugunname = ""
      this.gugunSearchInfo.genre = ""
      this.gugunSearchInfo.sidoname = sido

      this.createInfo.sido = sido
      this.createInfo.sigungu = ""

      this.performSearchInfo.sidoname = sido
      this.performSearchInfo.gugunname = ""

      const defaultSido = document.querySelector('.create-sido-default')
      const defaultGugun = document.querySelector('.create-gugun-default')
      defaultSido.innerText = sido
      defaultGugun.innerText = "구/군"
      this.fetchGuguns(this.gugunSearchInfo)
    }
    const gugunClickCreate = function gugunClickcreate(gugun){
      this.createInfo.sigungu = gugun
      this.performSearchInfo.gugunname = gugun

      const defaultGugun = document.querySelector('.create-gugun-default')
      defaultGugun.innerText = gugun
    }
    const searchSubmit = function searchSubmit() {
      this.performSearchInfo.search_word = this.searchText
      console.log(this.performList)
      axios.post(secosi.performs.search(), this.performSearchInfo)
        .then(res => {
          this.performList = res.data
          const defaultPerform = document.querySelector('.create-perform-default')
          defaultPerform.innerText = "공연정보를 불러왔습니다! 선택해주세요"
        })
        .catch(err => {
          console.error(err.response.data)
        })
    }
    const genreClick = function genreClick(genre) {
      this.performSearchInfo.genre = genre.code
      this.createInfo.genrenm = genre.genre
      const defaultGenre= document.querySelector('.create-genre-default')
      defaultGenre.innerText = genre.genre
    }
    const headCountClick = function headCountClick(head){
      this.createInfo.person_limit = head
      const defaultHead = document.querySelector('.create-head-default')
      defaultHead.innerText = `제한인원: ${head}`
    }
    const isAfterClickCreate = function isAfterClickCreate(isAfter) {
      this.createInfo._after = isAfter
      this.createInfo._spoiler = isAfter
      if (isAfter) {
        this.performSearchVisible = true
      }else{
        this.createInfo.genrenm = ""
        this.createInfo.mt20id = ""
        this.createInfo.perform_day = ""
        this.createInfo.performnm = ""
        this.createInfo.sido = ""
        this.createInfo.sigungu = ""
        this.performSearchVisible = false
      }
    }
    const isLockClick = function isLockClick(isLock){
      this.createInfo._secret = isLock
      this.passwordVisible =isLock
    }
    const changeDate = function changeDate(date){
      let month = date.getMonth() + 1;
      let day = date.getDate();
      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      const defaultDay = document.querySelector('.create-day-default')
      defaultDay.innerText = `공연 날짜 : ${date.getFullYear() + '-' + month + '-' +day}`
      this.date = date.getFullYear() + '-' + month + '-' +day

      const defaultTime = document.querySelector('.create-time-default')
      defaultTime.innerText = "공연시간"

      this.createInfo.perform_day = ""
      this.createInfo.perform_day = date.getFullYear() + '-' + month + '-' + day
    }
    const timeClick = function timeClick(time){
      const defaultTime= document.querySelector('.create-time-default')
      defaultTime.innerText = time.time
      this.createInfo.perform_day = this.createInfo.perform_day + " " + time.code
    }
    const performClick = function performClick(perform){
      this.createInfo.prfnm = perform.prfnm
      this.createInfo.genrenm = perform.genrenm
      this.createInfo.mt20id = perform.mt20id
      const defaultPerform = document.querySelector('.create-perform-default')
      defaultPerform.innerText = perform.prfnm
    }
    const conferenceCreate = function conferenceCreate(){
      console.log(this.currentUser)
      console.log(this.createInfo)
      if(this.createInfo._secret){
        if (this.nameText.length * this.passwordGet.length > 0){
          this.createInfo.password = this.passwordGet
          this.createInfo.title = this.nameText
          this.createInfo.owner_id = this.currentUser.userId
          console.log(this.createInfo)
          this.createRoom(this.createInfo)
        }else{
          if(this.nameText.length > 0){
            alert('비밀번호를 입력해주세요.')
          }else if(this.passwordGet.length > 0){
            alert('제목을 입력해주세요.')
          }else{
            alert('제목과 비밀번호를 입력해주세요.')
          }
        }
      }else{
        if (this.nameText.length > 0){
          this.createInfo.title = this.nameText
          this.createInfo.owner_id = this.currentUser.userId
          this.createRoom(this.createInfo)
        }else{
          alert('제목을 입력해주세요.')
        }
      }
      // this.$emit('createClose')
    }
    return {
      // current,
      sidoClick,
      gugunClickCreate,
      searchSubmit,
      genreClick,
      isAfterClickCreate,
      headCountClick,
      isLockClick,
      changeDate,
      timeClick,
      performClick,
      conferenceCreate
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
}
.modal {
  position: fixed;
  z-index: 10;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
  display: block;
}
.modal-content{
  max-width: 700px;
  background: #f4f4f4;
  margin: 10% auto;
  width: 60%;
  box-shadow: 0 5px 8px 0 rgba(0, 0, 0, 0.2), 0 7px 20px 0 rgba(0, 0, 0, 0.2);
  position: relative;
  border-radius: 3px;
  min-height: 500px;
  display: grid;
  grid-template-columns: 0.7fr 1.3fr;
}
.close-btn {
  position: absolute;
  top: 2%;
  right: 3%;
  font-size: 1.5rem;
  cursor: pointer;
}
.modal-content-left {
  background: linear-gradient(90deg, black 0%, black 100%);
  border-radius: 0 3px 3px 0;
  position: relative;
}
#modal-img {
  width: 70%;
  height: auto;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.modal-content-right {
  border-radius: 0 3px 3px 0;
  position: relative;
}
.modal-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  height: 83%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.modal-form h1 {
  font-size: 1.2rem;
  font-weight: 530;
  text-align: start;
  width: 80%;
  margin-bottom: 1.5rem;
}
.form-validation {
  margin-bottom: 0.5rem;
  width: 80%;
}
.form-validation.error {
  margin-bottom: 1.5rem;
  position:relative;
  border: 1px solid #f00e0e;
  border-radius: 2px;
}
.form-validation.valid {
  margin-bottom: 0.5rem;
  position: relative;
  border: 1px solid #1B3C33;
  border-radius: 2px;
}
.form-validation p{
  font-size: 0.7rem;
  margin-top: 0.5rem;
  color: #f00e0e;
  position: absolute;
  top: 1.8rem;
  display: none;
}
.form-validation.error p {
  display: block;
}
.modal-input {
  display: block;
  padding-left: 0.5rem;
  outline: none;
  border: none;
  height: 2rem;
  width: 100%;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.05);
}
.modal-input::placeholder{
  color: #b1b1b1;
}
.modal-input-btn {
  width: 80%;
  height: 2rem;
  background-color: #1B3C33;
  outline: none;
  border-radius: 2px;
  color: white;
  border: none;
  font-size: 1rem;
}
.modal-input-btn:hover {
  cursor:pointer;
  background-color: #fff;
  transition: all 0.4s ease-out;
  color: #1B3C33;
}
.modal-input-login {
  font-size: 0.8rem;
  margin-top: 1rem;
  color: var(--lightgray);
  width: 80%;
  text-align: center;
}
@media screen and (max-width: 1020px) {
  .modal-content-left{
    display: none;
  }
  .modal-content {
    grid-template-columns: auto;
    margin: 15% auto;
    width: 70%;
    height: 75%;
    max-height: 700px;
  }
}
@media screen and (max-width: 600px) {
  .modal-content {
    margin: 20% auto;
    width: 90%;
    height: 80%;
    min-height: 400px;
  }
}

.wrapper .search-box{
  margin-bottom:16px;
  width: 390px;
  background: #fff;
  border-radius: 5px;
  height: 40px;
  display: flex;
  padding: 10px;
  box-shadow: 0 8px 6px -10px #1e2721;
  z-index: -5;
}

.wrapper .search-box .dropdown{
  width: 150px;
  border-right: 2px solid #dde2f1;
  color: #9fa3b1;
  position: relative;
  cursor: pointer;
  margin: 0px;
}

.wrapper .search-box .dropdown .default-option{
  text-transform: uppercase;
  padding: 0px 0px;
  font-size: 14px;
}

.wrapper .search-box .dropdown ul{
  z-index: 3;
  overflow-y: scroll;
  height: 200px;
  position: absolute;
  top: 35px;
  left: -10px;
  background: #fff;
  width: 140px;
  border-radius: 5px;
  padding: 20px;
  display: block;
  box-shadow: 8px 8px 6px -10px #b3c6ff;
}
.wrapper .search-box .dropdown ul li{
  padding-bottom: 20px;
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
  right: 10px;
  border: 8px solid;
  border-color: #1B3C33 transparent transparent transparent;
}

.wrapper .search-box .search-field{
  width: 350px;
  height: 100%;
  position: relative;
}

.wrapper .search-box .search-field .input{
  width: 80%;
  height: 100%;
  border: 0px;
  font-size: 16px;
  padding-left: 20px;
  padding-right: 10px;
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
.conference-list{
  position: relative;
  top: 120px;
}

.group {
  display: flex;
  align-items: center;
  margin-bottom: 0px;
  margin-left: 6px;
}
.group input[type="checkbox"],
.group input[type="radio"] {
  position: absolute;
  opacity: 0;
  z-index: -1;
}
label {
  position: relative;
  margin-right: 3px;
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
</style>

