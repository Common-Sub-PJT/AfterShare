<template>
  <ArticleNavComponent></ArticleNavComponent>
  <div class="article-box">
    <div class="community-name-box">
      <p class="article-list-name">{{ this.articleListName }}</p>
    </div>
    <div class="detail-box">
      <div class="article-detail-box">
        <h4>{{ this.article.article_title }}</h4>
        <hr>
        <div class="place-right">
          <p>{{ this.article.regtime.slice(0, 16) }}</p>
        </div>
        <p class="article-content-box" v-html="article.article_content"></p>
        <div v-show="this.currentUser.name === this.article.user.name">
          <button class="btn-style" @click.prevent="likeArticle">추천</button>
          <router-link :to="{
          name: 'ArticleEdit',
          params: {
            articleid: this.articleId,
            title: this.article.article_title,
            content: this.article.article_content
          }
        }"><button class="btn-style">수정</button></router-link>
          <button class="btn-style" @click="articleDelete">삭제</button>
        </div>
        <hr>
        <CommentForm v-show="this.isLoggedIn"></CommentForm>
        <CommentList></CommentList>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi";
import { mapState, mapActions } from 'pinia'
import { useAccounts } from "@/stores/accounts";
import { useCommunities } from "@/stores/community";
import ArticleNavComponent from '@/views/articles/components/ArticleNavComponent.vue'
import CommentForm from '@/views/articles/components/CommentForm.vue'
import CommentList from '@/views/articles/components/CommentList.vue'

export default {
  data() {
    return {
      article: [],
      articleId: '',
      articleUserId: '',
    }
  },
  components: {
    ArticleNavComponent,
    CommentForm,
    CommentList,
  },
  computed: {
    ...mapState(useAccounts, ['currentUser', 'isLoggedIn']),
    ...mapState(useCommunities, ['genre', 'category', 'articleList', 'articleListName']),
  },
  methods: {
    ...mapActions(useCommunities, ['searchArticles']),
    articleDelete() {
      axios.delete(secosi.communities.articleDelete(this.articleId))
        .then(res => {
          this.searchArticles(this.genre, this.category)
          this.$router.push({ name: 'Articles' })
        })
        .catch(err => {
        })
    },
    likeArticle() {
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      axios.post(secosi.communities.like(this.articleId), config)
        .then(res => {
        })
        .catch(err => {
        })
    }
  },
  created() {
    this.articleId = this.$route.params.articleid
    axios.get(secosi.communities.articleDetail(this.articleId))
      .then(res => {
        this.article = res.data
      })
  }
};
</script>

<style>
.article-content-box {
  height: 400px;
}
.text-area {
  width: 800px;
  height: 600px;
}
hr {
  margin-bottom: 0;
}
.place-right {
  display: flex;
  justify-content: end;
}
.detail-box {
  display: flex;
  justify-content: center;
}
.article-detail-box {
  width: 880px;
}
.article-box {
  width: 1024px;
  border: 1px solid;
  border-top-color: #1B3C33;
  border-top: 2px solid;
}
.community-name-box{
  display: flex;
}
.article-list-name {
  padding: 30px 0px 30px 40px;
  margin: 0;
  font-size: 20px;
  font-weight: bold;
}
.btn-style {
  font-size: 18px;
  font-weight: bold;
  border-radius: 4px;
  margin-left: 10px;
  padding: 0;
  width: 96px;
  height: 32px;
  border: 0;
  color: #FFFFFF;
  background-color: #1B3C33;
}
</style>
