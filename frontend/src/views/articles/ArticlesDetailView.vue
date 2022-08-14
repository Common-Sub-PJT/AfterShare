<template>
  <ArticleNavComponent></ArticleNavComponent>
  <div class="row">
    <div>
      <h1>{{ this.title }}</h1>
      <button @click.prevent="likeArticle">좋아요</button>
      <p>{{ this.content }}</p>
      <div v-show="this.currentUser.name === this.authorName">
        <router-link :to="{
          name: 'ArticleEdit',
          params: {
            articleid: this.articleId,
            title: this.title,
            content: this.content
          }
        }"><button>수정</button></router-link>
        <button @click="articleDelete">삭제</button>
      </div>
      <hr>
      <CommentForm />
      <p>댓글목록</p>
      <CommentList />
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
      title: '',
      content: '',
      articleId: '',
      articleUserId: '',
      authorName: '',
    }
  },
  components: {
    ArticleNavComponent,
    CommentForm,
    CommentList,
  },
  computed: {
    ...mapState(useAccounts, ['currentUser']),
    ...mapState(useCommunities, ['genre', 'category', 'articleList']),
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
          console.log(res)
          console.log('게시글 추천')
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  created() {
    this.articleId = this.$route.params.articleid
    axios.get(secosi.communities.articleDetail(this.articleId))
      .then(res => {
        this.title = res.data.article_title
        this.content = res.data.article_content
        this.authorName = res.data.user.name
      })
  }
};
</script>

<style>
</style>
