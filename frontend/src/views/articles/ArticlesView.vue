<template>
  <div class="Community row">
    <div class="col-10 offset-1 col-md-8 offset-md-2 col-xxl-6 offset-xxl-3">
      <h1 class="fw-bold">community</h1>
      <div class="dropdown" v-for="(performGenre, index) in performGenres" :key="index">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
          data-bs-toggle="dropdown" aria-expanded="false">
          {{ performGenre }}
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
          <li v-for="(dropdownItem, index) in dropdownItems" :key="index">
            <a class="dropdown-item" @click="updateArticles">{{ dropdownItem }}</a>
          </li>
        </ul>
      </div>

      <div class="d-flex justify-content-end button-div">
        <router-link to="/ArticleCreate" class="">
          <button type="button" class="btn btn-primary">새글 쓰기</button>
        </router-link>
      </div>

      <!-- for문 돌려서 받아온 글 띄우기 -->
      <ul class='list-group list-group-flush' v-for="article in articles" :key="article.id">
        <li class="list-group-item d-flex justify-content-between align-items-center">
          <router-link class="text-decoration-none text-black"
            :to="{ name: 'ArticleDetail', params: { articleId: article.id } }">
            {{ article.title }}
          </router-link>
        </li>
      </ul>
    </div>

    <form @submit="plus">
      <input type="text" v-model="plusItem">
      <button>plus</button>
    </form>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      articles: "",
      dropdownItems: ['리뷰게시판', '자유게시판'],
      performGenres: ['전체', '뮤지컬'],
      plusItem: "",
    };
  },
  computed: {
  },
  methods: {
    updateArticles(event) {
      event.preventDefault()

      // community id를 받아오는 부분에서 의논이 필요할 것임
      // 당장 생각나는 방법으로는
      // created에서 장르-communityID 매칭이 되어있는 데이터를 받아서 data()에 넣고 시작하는 것

      // axios.get('http://127.0.0.1:8000/articles/:communityid/')
      //   .then(response => {
      //     this.articles = response.data
      //   })
    },

    // 실험용
    plus(event) {
      event.preventDefault()
      this.performGenres.push(this.plusItem)
      console.log(this.performGenres)
    }
  },
  created() {
    // 페이지 생성 시 community로 요청 보내서 데이터 받아오고 articles data에 저장
    //        ==> template에 for문 돌려서 띄워버리기
    // axios.get('http://127.0.0.1:8000/community/')
    //   .then(response => {
    //     this.articles = response.data
    //   })
  }
};
</script>

<style scoped>
</style>