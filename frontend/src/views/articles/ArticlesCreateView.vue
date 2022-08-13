
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
      <button type="submit" class="btn btn-primary">Submit</button>
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
      title: '',
      content: '',
    }
  },
  methods: {
    createArticle(event) {
      event.preventDefault()
      const formData = {
        'article_genre': this.articleGenre,
        'article_category': this.articleCategory,
        'article_title': this.title,
        'article_content': this.content,
      }
      console.log(formData)
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      
      axios.post(secosi.communities.community(), formData, config)
        .then(res => {
          this.$router.push({name:'ArticleDetail', params: {articleid: res.data}})
        })
        .catch(err => {
          console.log(err)
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
    ...mapState(useCommunities, ['articleGenre', 'articleCategory']),
  }
};
</script>

<style>
</style>