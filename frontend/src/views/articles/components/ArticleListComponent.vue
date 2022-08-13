<template>
  <h5 class="community-name">{{ this.articleListName }}</h5>
  <div class="classification-box">
    <p>제목</p>
    <div class="d-flex">
      <div v-for="(item, index) in items" :key="index">
        <p class="article-list-info">{{ item }}</p>
      </div>
    </div>
  </div>
  <hr>
  <ul v-for="article in this.articleList" :key="article.article_id">
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
        <p class="article-list-info">작성자</p>
        <p class="article-list-info">{{ article.view_cnt }}</p>
        <p class="article-list-info">추천수</p>
      </div>
    </li>
  </ul>
</template>

<script>
import axios from 'axios'
import secosi from '@/api/secosi'
import { mapState } from 'pinia'
import { useCommunities } from '@/stores/community'

export default {
  name: 'ArticleListComponent',
  data() {
    return {
      items: ['작성자', '조회수', '추천수'],
    }
  },
  created() {
    console.log(this.articleList)
  },
  methods: {
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
    }
  },
  computed: {
    ...mapState(useCommunities, ['articleListName', 'articleList'])
  }
}
</script>

<style scoped>
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
.community-name {
  font-weight: bold;
  margin: 1.5rem 0rem 1.5rem 0rem;
  padding: 0;
}
.article-list {
  display: flex;
  text-align: center;
  justify-content: space-between;
}
.article-list-item {
  text-decoration: none;
  color: black;
}
.classification-box {
  display: flex;
  justify-content: space-between;
}
li {
  background-color: rgb(250, 250, 250);
  padding-left: 12px;
  padding-right: 12px;
}
ul {
  padding: 0px;
}
.article-list-info{
  margin: 0rem 1rem 0rem 1rem;
  height: 24px;
  width: 48px;
  text-align: center;
  align-items: center;
}
</style>