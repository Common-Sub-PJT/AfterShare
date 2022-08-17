<template>
  <div>
    <div class="scroll-bar">
      <ul v-for="article in myArticleList" :key="article.article_id">
        <li class="article-list">
          <router-link @click="viewCount(article.article_id)" class="article-list-item"
            :to="{ name: 'ArticleDetail', params: { articleid: article.article_id } }">
            {{ article.article_title }}
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'pinia'
import { useMypages } from '@/stores/mypage'
import { useAccounts } from '@/stores/accounts'

export default {
  name: 'CommentItem',
  data() {
    return {
    }
  },
  components: {
  },
  created() {
    this.getMyArticles(this.currentUser.userId)
  },
  methods: {
    ...mapActions(useMypages, ['getMyArticles']),
  },
  computed: {
    ...mapState(useAccounts, ['currentUser']),
    ...mapState(useMypages, ['myArticleList'])
  }
}
</script>

<style>
.scroll-bar {
  width: 420px;
  height: 440px;
  overflow: scroll;
}
.scroll-bar::-webkit-scrollbar {
  width: 10px;
}
.scroll-bar::-webkit-scrollbar-thumb {
  background: #1b3c3376;
  border: 2px solid transparent;
}
.scroll-bar::-webkit-scrollbar-track {
  background: #ffffff;
}
.article-list {
  list-style: none;
  margin: 20px 0px 0px 0px;
}
.article-list-item {
  text-decoration: none;
  color: black;
  margin-bottom: 5px;
}
.article-list-item:hover {
  text-decoration: none;
  color: black;
  font-weight: bold;
  margin-bottom: 5px;
}
</style>