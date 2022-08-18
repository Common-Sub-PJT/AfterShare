<template>
  <main>
    <div class="nav-box">
      <nav>
        <ul class="menu-item">
          <h4 class="main-text">예매상황판</h4>
          <li @click="genreClick(genre)" v-for="(genre, index) in genreList" :key="index"><p>{{genre.genre}}</p></li>
        </ul>
      </nav>
    </div>

    <div class="container">
      <div class="row prf-box">
        <div v-for="perform in prfrankList" :key="perform.mt20id" class="card col-lg-4">
          <router-link :to="{ name: 'PerformDetail', params: {mt20id: perform.mt20id} }">
            <img class="card-img-top" :src='`http://www.kopis.or.kr/${perform.poster}`' alt="Card image cap">
            <div class="ranking">{{perform.rnum}}</div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="line"></div>

    <div class="container">
      <h4 class="title">뒤풀이방</h4>
      <div class="row">
        <div class="card-col">
          <div class="card-box" v-for="conference in cfrrankList" :key="conference.conference_id">
            <figure class="conf-card">
              <img v-if="conference.genrenm === genreName[1]" class="conf-img" src="@/assets/conference/1.jpg" alt="sample99" />
              <img v-else-if="conference.genrenm === genreName[2]" class="conf-img" src="@/assets/conference/2.jpg" alt="sample99" />
              <img v-else-if="conference.genrenm === genreName[3]" class="conf-img" src="@/assets/conference/3.jpg" alt="sample99" />
              <img v-else-if="conference.genrenm === genreName[4]" class="conf-img" src="@/assets/conference/4.jpg" alt="sample99" />
              <img v-else-if="conference.genrenm === genreName[5]" class="conf-img" src="@/assets/conference/5.jpg" alt="sample99" />
              <img v-else-if="conference.genrenm === genreName[6]" class="conf-img" src="@/assets/conference/6.jpg" alt="sample99" />
              <img v-else-if="conference.genrenm === genreName[7]" class="conf-img" src="@/assets/conference/7.jpg" alt="sample99" />
              <img v-else class="conf-img" src="@/assets/conference/8.jpg" alt="sample99" />
              <img v-if="conference._secret" src="@/assets/conference/padlock.png" alt="" class="lock-img">
              <p class="limit">{{conference.person_now}}/{{conference.person_limit}}</p>
              <figcaption>
                <h3 v-if="conference._after">관람자만</h3>
                <h3 v-else>모두참여</h3>
                <div v-if="conference.person_now === conference.person_limit"><button @click="cantAlert" class="conf-in">입장하기</button></div>
                <div v-else-if="conference._secret"><button @click="secretAlert(conference.password)" class="conf-in">입장하기</button></div>
                <div v-else><button @click="basicAlert" class="conf-in">입장하기</button></div>
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
    </div>

    <div class="line"></div>

    <div class="container">
      <h4 class="title">인기리뷰</h4>
      <div class="classification-box">
        <p class="article-list-info-title">제목</p>
        <div class="article-list-info-title-box">
          <div v-for="(item, index) in items" :key="index">
            <p class="article-list-info-title">{{ item }}</p>
          </div>
        </div>
      </div>
      <hr>
      <ul v-for="article in artrankList" :key="article.article_id" class="articles">
        <li class="article-list">
          <div class="article-title">
            <router-link @click="viewCount(article.article_id)" class="article-list-item"
              :to="{ name: 'ArticleDetail', params: { articleid: article.article_id } }">
              {{ article.article_title }}
            </router-link>
            <div class="comment-count">
              <p class="comment-value">
                {{ article.comment_cnt }}
              </p>
            </div>
          </div>
          <div class="article-info">
            <p class="article-list-info">{{ article.user_name }}</p>
            <p class="article-list-info">{{ article.view_cnt }}</p>
            <p class="article-list-info">{{ article.recommend }}</p>
          </div>
        </li>
      </ul>
    </div>



  </main>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi"
import { defineComponent } from 'vue'
import { mapState, mapActions } from 'pinia'
import { useHomes } from '@/stores/home'
import Swal from 'sweetalert2'


export default defineComponent({
  data() {
    return {
      prfrankList: this.performRank === undefined ? [] : this.performRank.all,
      cfrrankList: [],
      artrankList: [],
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
      items: ['작성자', '조회수', '추천수'],
      genrenm: {
        "catecode": ""
      },
      genreName:[
        "ALL", "뮤지컬", "연극", "무용", "클래식", "오페라", "국악", "복합"
      ],
    }
  },
  methods: {
    ...mapActions(useHomes, ['fetchPerformRank']),
    fetchPrfrank() {
      axios.get(secosi.main.prfrank(), { params: { "catecode" : "" } })
        .then(res => {
          this.prfrankList = res.data
        })
    },
    searchPrfrank(genrenm) {
      axios.get(secosi.main.prfrank(), { params: genrenm })
        .then(res => {
          this.fetchPerformRank(genrenm, res.data)
        })
    },
    fetchCfrrank() {
      axios.get(secosi.main.cfrrank())
        .then(res => {
          this.cfrrankList = res.data
        })
    },
    fetchArtrank() {
      axios.get(secosi.main.artrank())
        .then(res => {
          this.artrankList = res.data
        })
    },
    viewCount(articleId) {
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      axios.post(secosi.communities.viewCount(articleId), config)
      .then (res => {
      })
      .catch (err => {
      })
    },
    basicAlert() {
      Swal.fire({
        title: '입장하시겠습니까?',
        confirmButtonColor: '#1b3c33',
        confirmButtonText: '입장하기',
        showCloseButton: true,
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
    secretAlert(password) {
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
          }
        },
      })
    },
  },
  computed: {
    ...mapState(useHomes, ['performRank'])
  },
  created() {
    window.location.reload
    this.prfrankList = this.performRank === undefined ? [] : this.performRank.all
    this.fetchPrfrank()
    this.fetchCfrrank()
    this.fetchArtrank()
  },
  setup() {
    const genreClick = function genreClick(genre) {
      const listmatch = {
        "전체": this.performRank.all,
        "연극": this.performRank.act,
        "뮤지컬": this.performRank.musical,
        "무용": this.performRank.dance,
        "클래식": this.performRank.classic,
        "오페라": this.performRank.opera,
        "국악": this.performRank.gukak,
        "복합": this.performRank.complex,
      }
      this.prfrankList = listmatch[genre.genre]
      this.genrenm.catecode = genre.code
      this.searchPrfrank(this.genrenm)
    }
    return {
      genreClick,
    }
  }
})
</script>

<style scoped>
.nav-box {
  border: 3px solid #1b3c33;
  margin-top: 2rem;
}
.menu-item {
  padding: 1rem;
  margin: 0;
  list-style: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.menu-item li {
  cursor: pointer;
}
.menu-item li p {
  margin: 0;
}
.menu-item li :hover {
  text-decoration: underline;
  font-weight: bold;
}
.menu-item li :visited {
  text-decoration: underline;
  font-weight: bold;
}
.card {
  margin: 1rem;
  padding: 0;
  width: 160px;
  height: 226px;
  border: none;
  border-radius: 5px;
}
.card-img-top {
  width: 160px;
  height: 226px;
  border: none;
  border-radius: 5px;
}
.ranking {
  background-color: #1b3c33;
  position: absolute;
  top: 0;
  width: 24px;
  height: 24px;
  text-align : center;
  border: none;
  border-radius: 5px 0 0 0;
}
.prf-box {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
}
a {
  text-decoration: none;
  color: white;
}
.main-text {
  margin: 0;
  font-weight: bold;
  color: #1B3C33;
}
.title {
  margin-bottom: 1rem;
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
  z-index: 1;
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
  column-count: 4;
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
  margin: 0;
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
.line {
  width: 1024px;
  background-color: #1B3C33;
  border: 1px #1B3C33 solid;
  margin-top: 1rem;
  margin-bottom: 2rem;
}
.article-list-info-title-box{
  display: flex;
  width: 240px;
  justify-content: space-around;
}
.comment-value {
  text-align: center;
  align-items: center;
  margin: 0;
}
.article-info {
  display: flex;
  align-items: center;
}
.article-title {
  display: flex;
  align-items: center;
}
.comment-count {
  background-color: lightgray;
  border: none;
  border-radius: 50%;
  margin: 0rem 1rem 0rem 1rem;
  height: 24px;
  width: 24px;
  text-align: center;
  align-items: center;
}
.article-list {
  display: flex;
  text-align: center;
  justify-content: space-between;
  background-color: rgb(250, 250, 250);
  padding-left: 12px;
  padding-right: 12px;
}
.article-list-info-title{
  text-decoration: none;
  font-weight: bold;
  color: black;
  margin: 0.5rem;
}
.article-list-item {
  text-decoration: none;
  color: black;
}
.classification-box {
  display: flex;
  justify-content: space-between;
}
.articles {
  padding: 0px;
}
.article-list-info{
  margin: 0rem 1rem 0rem 1rem;
  height: 24px;
  width: 48px;
  text-align: center;
  align-items: center;
}
h2 {
  color: #1B3C33;
}
</style>

