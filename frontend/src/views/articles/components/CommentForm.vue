




<!-- 긁어오기만 한 내용, 수정 필요함 -->




<template>
  <form @submit.prevent="onSubmit" class="comment-form">
    <label for="comment">comment: </label>
    <input type="text" id="comment" v-model="content" required>
    <button>Comment</button>
  </form>
</template>

<script>
export default {
  name: 'CommentForm',
  data() {
    return {
      content: ''
    }
  },
  props: {
    articleId: Number
  },
  methods: {
    onSubmit() {
      this.createComment({ articleId: this.articleId, content: this.content, })
      this.content = ''
    }
  },
}
</script>

<style>
</style>