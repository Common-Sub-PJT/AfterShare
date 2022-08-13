<template>
  <form @submit.prevent="createComment" class="comment-form">
    <label for="comment">comment: </label>
    <input type="text" id="comment" v-model="content" required>
    <button>Comment</button>
  </form>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi";
import { mapActions } from 'pinia';
import { useCommunities } from '@/stores/community'

export default {
  name: 'CommentForm',
  data() {
    return {
      content: '',
      articleId: this.$router.currentRoute._value.params.articleid
    }
  },
  methods: {
    ...mapActions(useCommunities, ['searchComments']),
    createComment() {
      const params = new URLSearchParams();
      params.append('comment', this.content);
      params.append('article_id', this.articleId);
      
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      axios.post(secosi.communities.comment(this.articleId), params, config)
      .then(res => {
        this.content = ''
        this.searchComments(this.articleId)
      })
      .catch(err => {
        console.log(err)
      })
    },
  },
}
</script>

<style>
</style>