<template>
  <div class="wrapper">
    <div class="search_box">
      <div @click="searchShow" class="dropdown">
        <div class="search_default_option">{{ this.searchCategory }}</div>
        <ul v-if="searchVisible">
          <li @click="searchClick(search)" v-for="(search, index) in searchList" :key="index">{{ search }}</li>
        </ul>
      </div>
      <div class="search_field">
        <input @keyup.enter="searchArticles" v-model="searchText" type="text" class="input" placeholder="검색">
        <button @click="searchArticles" class="button">Go!</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi"
import { defineComponent } from 'vue'
import { mapState, mapActions } from 'pinia'
import { useCommunities } from "@/stores/community";

export default defineComponent({
  name: 'SearchComponent',
  data() {
    return {
      searchVisible: false,
      searchList: [
        "제목",
        "작성자",
      ],
      searchText: "",
      searchCategory: "제목",
    }
  },
  methods: {
    ...mapActions(useCommunities, ['updateArticleList']),
    searchShow() {
      this.searchVisible = !this.searchVisible
    },
    searchArticles() {
      if (this.searchText.length > 0) {
        const config = {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        }
        axios.get(secosi.communities.articleSearch(this.articleGenre, this.articleCategory, this.searchCategory, this.searchText), config)
          .then(res => {
            this.updateArticleList(res.data)
            this.searchText = ""
          })
          .catch(err => {
            console.error(err.response.data)
          })
      }
    },
    searchClick(search) {
      this.searchCategory = search
    }
  },
  computed: {
    ...mapState(useCommunities, ['articleGenre', 'articleCategory']),
  },
})
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  outline: none;
  list-style: none;
  font-family: 'Montserrat', sans-serif;
}

body {
  background: #e4eefa;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.wrapper {
  left: 50%;
  z-index: 1;
  display: flex;
  justify-content: center;
  margin-top: 1.5rem;
}

.wrapper .search_box {
  width: 500px;
  background: #fff;
  border: 2px solid #1B3C33;
  border-radius: 5px;
  height: 50px;
  display: flex;
  /* justify-content: space-between; */
  padding: 10px;
  box-shadow: 0 8px 6px -10px #1e2721;
}

.wrapper .search_box .dropdown {
  width: 150px;
  border-right: 2px solid #dde2f1;
  color: #9fa3b1;
  position: relative;
  cursor: pointer;
}

.wrapper .search_box .dropdown .default_option {
  text-transform: uppercase;
  padding: 13px 15px;
  font-size: 14px;
}

.wrapper .search_box .dropdown ul {
  z-index: 10;
  overflow-y: scroll;
  height: 240px;
  position: absolute;
  top: 40px;
  left: -10px;
  background: #fff;
  width: 140px;
  border-radius: 5px;
  padding: 20px;
  display: block;
  box-shadow: 8px 8px 6px -10px #b3c6ff;
}

.wrapper .search_box .dropdown ul li {
  padding-bottom: 20px;
  z-index: 5;
}

.wrapper .search_box .dropdown ul li:last-child {
  padding-bottom: 0;
}

.wrapper .search_box .dropdown ul li:hover {
  color: #6f768d;
}

.wrapper .search_box .dropdown:before {
  content: "";
  position: absolute;
  top: 12px;
  right: 20px;
  border: 8px solid;
  border-color: #1B3C33 transparent transparent transparent;
}

.wrapper .search_box .search_field {
  width: 400px;
  height: 100%;
  display: flex;
  justify-content: space-between;
}

.wrapper .search_box .search_field .input {
  width: 80%;
  height: 100%;
  border: 0px;
  font-size: 16px;
  padding-left: 20px;
  padding-right: 10px;
  color: #6f768d;
}

.button {
  background: #1B3C33;
  color: #fff;
  width: 32px;
  border-radius: 5px;
}
</style>
