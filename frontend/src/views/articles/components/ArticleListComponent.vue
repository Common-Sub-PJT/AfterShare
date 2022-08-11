<template>
  <div>
    <h5 class="fw-bold">{{ this.articleListName }}</h5>
  </div>
  <ul class='list-group list-group-flush' v-for="article in this.articleList" :key="article.article_id">
    <li class="list-group-item d-flex justify-content-between align-items-center">
      <router-link class="text-decoration-none text-black"
        :to="{ name: 'ArticleDetail', params: { articleid: article.article_id } }">
        {{ article.article_title }}
      </router-link>
    </li>
  </ul>
</template>

<script>
import { mapState } from 'pinia'
import { useCommunities } from '@/stores/community'

export default {
  name: 'ArticleListComponent',
  data() {
    return {
    }
  },
  methods: {
  },
  computed: {
    ...mapState(useCommunities, ['articleListName', 'articleList'])
  }
}
</script>

<style>
</style>