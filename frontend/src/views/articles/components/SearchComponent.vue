<template>
  <div class="wrapper">
    <div class="search_box">
      <div @click="searchShow" class="dropdown">
        <div class="search_default_option">제목</div>
        <ul v-if="searchVisible">
          <li @click="searchClick(search)" v-for="(search, index) in searchList" :key="index">{{ search }}</li>
        </ul>
      </div>
      <div class="search_field">
        <input @keyup.enter="searchSubmit" v-model="searchText" type="text" class="input" placeholder="검색">
        <button @click="searchSubmit" class="btn btn-secondary">Go!</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import secosi from "@/api/secosi"
import { defineComponent } from 'vue'
import { mapState } from 'pinia'
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
    searchShow() {
      this.searchVisible = !this.searchVisible
    },
    searchArticles() {
      axios.post(secosi.performs.articleSearch(), performInfo)
        .then(res => {
          this.performList = res.data
        })
        .catch(err => {
          console.error(err.response.data)
        })
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
  position: absolute;
  top: 42rem;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
}

.wrapper .search_box {
  width: 500px;
  background: #fff;
  border-radius: 5px;
  height: 50px;
  display: flex;
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
  width: 200px;
  height: 100%;
  position: relative;
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


nav {
  position: absolute;
  margin: 0px;
  background: #f9f9f9;
  padding-left: 24px;
  top: 70px;
  /* left: 25%; */
}

nav .menuItems {
  list-style: none;
  display: flex;
}

nav .menuItems li {
  cursor: pointer;
  margin: 10px;
}

nav .menuItems li p {
  text-decoration: none;
  color: #8f8f8f;
  font-size: 15px;
  font-weight: 500;
  transition: all 0.3s ease-in-out;
  position: relative;
  text-transform: uppercase;
  z-index: 0;

}

nav .menuItems li p:hover {
  text-decoration: none;
  color: white;
  font-size: 15px;
  font-weight: bold;
  transition: all 0.3s ease-in-out;
  position: relative;
  text-transform: uppercase;
  z-index: 0;
  margin: 4px;
}

nav .menuItems li ::before {
  content: "";
  transition: 0.3s;
  background-color: #1B3C33;
  color: white;
  position: absolute;
  /* font-weight:bold; */
  margin: 0px;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  width: 0;
  overflow: hidden;
  z-index: -1;
}

nav .menuItems li :hover::before {
  width: 100%;
  transition: all 0.3s ease-in-out;
}

.container {
  padding: 2rem;
  margin-top: 10rem;
}

.card-box {
  margin: 0;
  justify-content: space-between;
}

.card {
  margin-bottom: 3rem;
  padding: 0;
  width: 200px;
  height: 372px;
}

.card-body {
  margin: 0;
  padding-top: 1rem;
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  vertical-align: middle;
}

.card-img-top {
  width: 200px;
  height: 264px;
}

.card-title {
  font-size: 12px;
  height: 40px;
}

.card-date {
  font-size: 10px;
  height: 20px;
}

.card-location {
  font-size: 10px;
  height: 32px;
}

li {
  list-style: none;
}

a {
  text-decoration: none;
  color: black;
}

p {
  margin: 0;
  overflow: hidden;
}
</style>
