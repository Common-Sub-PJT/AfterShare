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
              <img class="conf-img" src="@/assets/conference.png" alt="sample99" />
              <figcaption>
                <h3 v-if="conference._after">관람자만</h3>
                <h3 v-else>모두참여</h3>
                <div><button class="conf-in">입장하기</button></div>
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

export default defineComponent({
  data() {
    return {
      prfrankList: [],
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
      }
    }
  },
  methods: {
    fetchPrfrank() {
      axios.get(secosi.main.prfrank(), { params: { "catecode" : "" } })
        .then(res => {
          this.prfrankList = res.data
          console.log(res.data)
        })
    },
    searchPrfrank(genrenm) {
      axios.get(secosi.main.prfrank(), { params: genrenm })
        .then(res => {
          this.prfrankList = res.data
          console.log(res.data)
        })
    },
    fetchCfrrank() {
      axios.get(secosi.main.cfrrank())
        .then(res => {
          this.cfrrankList = res.data
          console.log(res.data)
        })
    },
    fetchArtrank() {
      axios.get(secosi.main.artrank())
        .then(res => {
          this.artrankList = res.data
          console.log(res.data)
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
  },
  created() {
    this.fetchPrfrank()
    this.fetchCfrrank()
    this.fetchArtrank()
  },
  setup() {
    const genreClick = function genreClick(genre) {
      this.genrenm.catecode = genre.code
      console.log(this.genrenm.catecode)
      console.log(genre.code)
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
  max-width: 100%;
  backface-visibility: hidden;
  vertical-align: top;
}
.conf-card:before,
.conf-card:after {
  position: absolute;
  top: 20px;
  right: 20px;
  content: '';
  background-color: #fff;
  z-index: 1;
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
  width: 200px;
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
  margin-top: 0.5rem;
  width: 1080px;
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

