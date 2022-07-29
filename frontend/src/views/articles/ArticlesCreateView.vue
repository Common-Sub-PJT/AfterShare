



<!-- 드랍다운 만들어야하는데 고민중 -->





<template class="row">
  <div class="col-10 offset-1 col-md-8 offset-md-2 col-xxl-6 offset-xxl-3">
    <form @submit="createArticle">
      <div class="row mb-3">
        <!-- <div class="dropdown">
          <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
            data-bs-toggle="dropdown" aria-expanded="false">
            Dropdown button
          </button>
          <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <li><a class="dropdown-item" href="#">Action</a></li>
            <li><a class="dropdown-item" href="#">Another action</a></li>
            <li><a class="dropdown-item" href="#">Something else here</a></li>
          </ul>
        </div> -->
        <label for="title" class="col-form-label">Title</label>
        <input type="text" class="form-control" id="title" v-model="title">
      </div>
      <div class="row mb-3">
        <label for="content" class="col-form-label">Content</label>
        <textarea id="content" v-model="content" rows="20" class="form-control"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      title: '',
      content: '',
      image: '/media/article_image/%EB%83%A5%EC%9D%B4.jpg',
    }
  },
  methods: {
    createArticle(event) {
      event.preventDefault()
      const formData = {
        'title': this.title,
        'content': this.content,
      }

      console.log(formData)
      // const config = {
      //   headers: {
      //     Authorization: `Token ${localStorage.getItem('token')}`
      //   }
      // }

      // axios.post('http://127.0.0.1:8000/community/create/', formData, config)
      //   .then(response => {
      //     console.log(response)
      //   })
    },
  },
};
</script>

<style>
</style>