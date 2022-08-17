<template>
  <div>
    <div class="scroll-box">
      <ul v-for="(comment, index) in myCommentList" :key="index">
        <li class="comment-list">
          <router-link @click="viewCount(article.article_id)" class="comment-list-item"
            :to="{ name: 'ArticleDetail', params: { articleid: comment.article_id } }">
            {{ comment.comment_content }}
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
  deactivated() {
    return {
    }
  },
  created() {
    this.getMyComments(this.currentUser.userId)
  },
  methods: {
    ...mapActions(useMypages, ['getMyComments'])
  },
  computed: {
    ...mapState(useAccounts, ['currentUser']),
    ...mapState(useMypages, ['myCommentList'])
  }
}
</script>

<style>
.scroll-box {
  overflow: scroll;
  width: 420px;
  height: 440px;
}
.comment-list {
  list-style: none;
  margin: 20px 0px 0px 0px;
}
.comment-list-item {
  text-decoration: none;
  color: black;
  margin-bottom: 5px;
}
.comment-list-item:hover {
  text-decoration: none;
  color: black;
  font-weight: bold;
  margin-bottom: 5px;
}
</style>