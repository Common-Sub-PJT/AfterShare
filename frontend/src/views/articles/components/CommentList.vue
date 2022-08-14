<template>
  <router-view :key="this.commentList">
    <ul class='list-group list-group-flush' v-for="comment in this.commentList" :key="comment.comment_id">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <p v-if="this.editNum!=comment.comment_id">{{ comment.comment_content }}</p>
        <input v-else type="text" id="comment" v-model="content" required>
        <!-- <div> -->
        <div v-show="this.currentUser.name===comment.user.name">
          <button v-if="this.editNum!=comment.comment_id" @click="commentEditSetup(comment)">수정</button>
          <button v-else @click="commentEdit(comment.comment_id)">적용</button>
          <button v-if="this.editNum!=comment.comment_id" @click="commentDelete(comment.comment_id)">삭제</button>
          <button v-else @click="initEditNum">취소</button>
        </div>
      </li>
    </ul>
  </router-view>
</template>

<script>
import axios from 'axios'
import secosi from '@/api/secosi.js'
import { mapActions, mapState } from 'pinia'
import { useCommunities } from '@/stores/community'
import { useAccounts } from '@/stores/accounts'

export default {
  name: 'CommentList',
  data() {
    return {
      articleId: this.$route.params.articleid,
      editNum: -1,
      content: '',
    }
  },
  methods: {
    ...mapActions(useCommunities, ['searchComments']),
    commentEdit(commentId) {
      const params = new URLSearchParams();
      params.append('comment', this.content)

      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      axios.patch(secosi.communities.commentEdit(this.articleId, commentId), params, config)
        .then(res => {
          this.initEditNum()
          this.searchComments(this.articleId)
        })
        .catch(err => {

        })
    },
    initEditNum() {
      this.editNum = -1
    },
    commentEditSetup(comment) {
      this.editNum = comment.comment_id
      this.content = comment.comment_content
    },
    commentUpdate() {

    },
    commentDelete(commentId) {
      axios.delete(secosi.communities.commentDelete(this.articleId, commentId))
      .then(res => {
        this.searchComments(this.articleId)
      })
      .catch(err => {
      })
    },
  },
  computed: {
    ...mapState(useCommunities, ['commentList']),
    ...mapState(useAccounts, ['currentUser']),
  },
  created() {
    this.searchComments(this.articleId)
  },
}
</script>

<style>
</style>