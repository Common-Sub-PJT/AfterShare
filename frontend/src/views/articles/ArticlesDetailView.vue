<template>
  <div class="row">
    <div>
    <ArticleNavComponent></ArticleNavComponent>
      <h1>{{ this.title }}</h1>
      <p>{{ this.content }}</p>

      <!-- 작성자일 경우, 게시글 수정 및 삭제 권한 부여 -->
      <!-- <div v-if="isAuthor">
      <router-link :to="{ name: 'ArticleEdit', params: { articleid } }">
        <button>Edit</button>
      </router-link>
      |
      <button @click="deleteArticle(articleid)">Delete</button>
    </div> -->

      <hr>
      <p>댓글목록</p>
      <!-- 댓글 목록 컴포넌트 -->
      <CommentList />
      <!-- 댓글 작성 컴포넌트 -->
      <CommentForm />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi";
import { mapState } from 'pinia'
import { useAccounts } from "@/stores/accounts";
import ArticleNavComponent from '@/views/articles/components/ArticleNavComponent.vue'
import CommentForm from '@/views/articles/components/CommentForm.vue'
import CommentList from '@/views/articles/components/CommentList.vue'

export default {
  data() {
    return {
      title: '',
      content: '',
      articleId: '',
    }
  },
  components: {
    ArticleNavComponent,
    CommentForm,
    CommentList,
  },
  computed: {
    ...mapState(useAccounts, ['currentUser'])
  },
  methods: {
  },
  created() {
    this.articleId = this.$route.params.articleid
    axios.get(secosi.communities.articleDetail(this.articleId))
      .then(res => {
        this.title = res.data.article_title
        this.content = res.data.article_content
        console.log(res.data)
        console.log(this.currentUser)
      })
  }
};
</script>

<style>
</style>
