<template>
  <ArticleNavComponent></ArticleNavComponent>
  <div class="row">
    <ArticleListComponent></ArticleListComponent>
    <div class="d-flex justify-content-end button-div">
      <router-link v-show="this.isLoggedIn" to="/articles/create" class="">
        <button v-if="(this.genre !== '전체') && ((this.genre !== '공지') || this.currentUser._staff)" type="button" class="btn">글쓰기</button>
      </router-link>
    </div>
    <SearchComponent></SearchComponent>
  </div>
</template>

<script>
import { mapState } from 'pinia'
import { useAccounts } from "@/stores/accounts";
import { useCommunities } from "@/stores/community";
import ArticleListComponent from '@/views/articles/components/ArticleListComponent.vue'
import ArticleNavComponent from '@/views/articles/components/ArticleNavComponent.vue'
import SearchComponent from '@/views/articles/components/SearchComponent.vue'

export default {
  data() {
    return {
      articles: "",
      dropdownContents: ['리뷰게시판', '자유게시판'],
      communityCategories: ['전체', '공지'],
      performGenres: ['뮤지컬', '연극', '클래식', '아동', '복합'],
      isShowWrite: this.isLoggedIn && (this.genre !== "전체"),
    };
  },
  created() {
  },
  computed: {
    ...mapState(useAccounts, ['isLoggedIn', 'currentUser']),
    ...mapState(useCommunities, ['genre']),
  },
  methods: {
  },
  components: {
    ArticleListComponent,
    ArticleNavComponent,
    SearchComponent,
  },
};
</script>

<style scoped>
.community.row {
  width: 1024px;
}
.btn {
  background-color: #1B3C33;
  color: #FAFAFA;
  width: 96px;
  height: 32px;
  font-size: 16px;
  padding:0;
}
</style>