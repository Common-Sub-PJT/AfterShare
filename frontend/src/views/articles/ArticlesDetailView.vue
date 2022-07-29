<template>
  <div class="row">
    <div>
      <h1>제목 받아올 것</h1>
      <p>내용 받아올 것</p>

      <!-- 작성자일 경우, 게시글 수정 및 삭제 권한 부여 -->
      <!-- <div v-if="isAuthor">
      <router-link :to="{ name: 'ArticleEdit', params: { articleid } }">
        <button>Edit</button>
      </router-link>
      |
      <button @click="deleteArticle(articleid)">Delete</button>
    </div> -->

      <hr>
      <p>댓글목록</p>
      <!-- 댓글 목록 컴포넌트 -->
      <CommentList />
      <!-- 댓글 작성 컴포넌트 -->
      <CommentForm />
    </div>
  </div>
</template>

<script>
import CommentForm from '@/views/articles/components/CommentForm.vue'
import CommentList from '@/views/articles/components/CommentList.vue'

export default {
  data() {
    return {
      articleId: '',
    }
  },
  components: {
    CommentForm,
    CommentList,
  },
  computed: {
    // 작성자 여부 확인
    // isAuthor() {
    //   return 현재 유저 == 게시글 작성자
    // },
  },
  methods: {
  },
  created() {
    // GET 요청 전송 => 응답값따라 data 채워넣고 template에 띄울것
    // const config = {
    //   headers: {
    //     Authorization: `Token ${localStorage.getItem("token")}`,
    //   },
    // }
    // axios.get(`http://127.0.0.1:8000/accounts/${articleId}/`, config)
    //   .then(response => {
    //     console.log(response)
    //   })
  }
};
</script>

<style>
</style>
