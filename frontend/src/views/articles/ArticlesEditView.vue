<!-- 
<template class="row">
  <ArticleNavComponent></ArticleNavComponent>
  <div class="col-10 offset-1 col-md-8 offset-md-2 col-xxl-6 offset-xxl-3">
    <form @submit="createArticle">
      <div class="row mb-3">
        <label for="title" class="col-form-label">Title</label>
        <input type="text" class="form-control" id="title" v-model="title">
      </div>
      <div class="row mb-3">
        <label for="content" class="col-form-label">Content</label>
        <textarea id="content" v-model="content" rows="20" class="form-control"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Update</button>
    </form>
  </div>
</template> -->


<template class="row">
  <ArticleNavComponent></ArticleNavComponent>
  <div class="article-create-box">
    <div class="community-name-box">
      <p class="article-list-name">{{ this.articleListName }}</p>
    </div>
    <form class="input-form" @submit.prevent="editArticle">
      <div class="form-box">
        <div class="input-box">
          <label for="title" class="input-label col-form-label">제목</label>
          <input type="text" class="title-input form-control" id="title" v-model="title">
        </div>
        <div class="input-box">
          <label for="content" class="input-label col-form-label">내용</label>
          <textarea id="content" v-model="content" rows="20" class="content-input form-control"></textarea>
        </div>
        <div class="button-box">
          <button type="submit" class="btn-style">수정</button>
          <button class="btn-style" @click.prevent="this.$router.go(-1)">취소</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi";
import { mapState } from 'pinia'
import { useAccounts } from "@/stores/accounts"
import { useCommunities } from '@/stores/community'
import ArticleNavComponent from '@/views/articles/components/ArticleNavComponent.vue'

export default {
  data() {
    return {
      title: this.$router.currentRoute._value.params.title,
      content: this.$router.currentRoute._value.params.content,
      articleId: this.$router.currentRoute._value.params.articleid,
    }
  },
  methods: {
    editArticle() {
      const formData = {
        'article_genre': this.articleGenre,
        'article_category': this.articleCategory,
        'article_title': this.title,
        'article_content': this.content,
      }
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      
      axios.patch(secosi.communities.articleUpdate(this.articleId), formData, config)
        .then(res => {
          this.$router.push({name:'ArticleDetail', params: {articleid: this.articleId}})
        })
        .catch(err => {
        })
    },
  },
  components: {
    ArticleNavComponent,
  },
  created() {
    if (!this.isLoggedIn) {
      this.$router.push({ name: 'Articles' })
    }
  },
  computed: {
    ...mapState(useAccounts, ['isLoggedIn']),
    ...mapState(useCommunities, ['articleGenre', 'articleCategory', 'articleListName']),
  }
};
</script>

<style>
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
.community-name-box{
  display: flex;
}
.input-label{
  width: 44px;
  padding: 0;
}
.button-box{
  display: flex;
  justify-content: end;
}
.input-box{
  margin-bottom: 1.5rem;
  display: flex;
}
.input-form{
  display: flex;
  justify-content: center;
}
.form-box{
  width: 860px;
}
.content-input {
  width: 816px;
  height: 300px;
}

.title-input {
  width: 816px;
  height: 32px;
}

.article-list-name {
  padding: 30px 0px 30px 40px;
  margin: 0;
  font-size: 20px;
  font-weight: bold;
}

.article-create-box {
  width: 1024px;
  height: 560px;
  border: 1px solid;
  border-top-color: #1B3C33;
  border-top: 2px solid;
}
</style>