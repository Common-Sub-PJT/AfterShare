<template>
  <profile-item></profile-item>
  <article-item></article-item>
  <comment-item></comment-item>
</template>

<script>
import ProfileItem from '@/views/mypage/components/ProfileItem.vue'
import ArticleItem from '@/views/mypage/components/ArticleItem.vue'
import CommentItem from '@/views/mypage/components/CommentItem.vue'

export default {
  name: "MypageView",
  components: {
    ProfileItem,
    ArticleItem,
    CommentItem,
  },
}
</script>