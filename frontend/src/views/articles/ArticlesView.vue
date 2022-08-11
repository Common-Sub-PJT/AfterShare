<template>
  <div class="Community row">
    <ArticleNavComponent></ArticleNavComponent>
    <ArticleListComponent></ArticleListComponent>

    <div class="d-flex justify-content-end button-div">
      <router-link to="/articles/create" class="">
        <button v-show="this.isLoggedIn" type="button" class="btn btn-primary">새글 쓰기</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import { mapState } from 'pinia'
import { useAccounts } from "@/stores/accounts";
import ArticleListComponent from '@/views/articles/components/ArticleListComponent.vue'
import ArticleNavComponent from '@/views/articles/components/ArticleNavComponent.vue'

export default {
  data() {
    return {
      articles: "",
      dropdownContents: ['리뷰게시판', '자유게시판'],
      communityCategories: ['전체', '공지'],
      performGenres: ['뮤지컬', '연극', '클래식', '아동', '복합'],
    };
  },
  created() {
  },
  computed: {
    ...mapState(useAccounts, ['isLoggedIn'])
  },
  methods: {
  },
  components: {
    ArticleListComponent,
    ArticleNavComponent,
  },
};
</script>

<style scoped>
</style>