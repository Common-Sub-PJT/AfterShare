<template>
  <router-view :key="this.commentList">
  <p>댓글 - {{ this.commentList.length }}</p>
    <ul class='ul-style list-group list-group-flush' v-for="comment in this.commentList" :key="comment.comment_id">
      <li class="list-group-item li-style">
        <div>
          <p class="comment-username">{{ comment.user.name }}</p>
          <p class="comment-style" v-if="this.editNum!=comment.comment_id">{{ comment.comment_content }}</p>
          <input v-else type="text" id="comment" v-model="content" required>
        </div>
        <div v-show="this.currentUser.name===comment.user.name">
          <button v-if="this.editNum!=comment.comment_id" @click="commentEditSetup(comment)" class="edit-button"><img class = "edit-img" src="@/assets/edit.png"></button>
          <button v-else @click="commentEdit(comment.comment_id)" class="btn-style">적용</button>
          <button v-if="this.editNum!=comment.comment_id" @click="commentDelete(comment.comment_id)"><img class = "edit-img" src="@/assets/x.png"></button>
          <button v-else @click="initEditNum" class="btn-style">취소</button>
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
.comment-username {
  font-size: 12px;
  margin: 0;
}
.li-style {
  margin-top: 4px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #FAFAFA;
}
.comment-style {
  margin: 0;
}
.btn-style {
  font-size: 10px;
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
</style>