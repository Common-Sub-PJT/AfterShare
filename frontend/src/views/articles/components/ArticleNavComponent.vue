<template>
  <div class="row">
    <div class="mt-4 d-flex justify-content-between">
      <div class="dropdown" v-for="(communityCategory, index) in communityCategories" :key="index">
        <router-link to="/articles"><button class="dropbtn"
            @click="searchArticles(communityCategory, communityCategory)">{{ communityCategory }}</button></router-link>
      </div>
      <div class="dropdown" v-for="(performGenre, index) in performGenres" :key="index">
        <button class="dropbtn">{{ performGenre }}</button>
        <div class="dropdown-content">
          <a @click="searchArticles(performGenre, dropdownContent)"
              v-for="(dropdownContent, index) in dropdownContents" :key="index"><router-link to="/articles">{{ dropdownContent }}</router-link></a>
          <!-- <router-link to="/articles"><a @click="searchArticles(performGenre, dropdownContent)"
              v-for="(dropdownContent, index) in dropdownContents" :key="index">{{ dropdownContent }}</a></router-link> -->
        </div>
      </div>
    </div>
    <div class="nav-line"></div>
  </div>
</template>

<script>
import { mapActions } from 'pinia'
import { useCommunities } from '@/stores/community'

export default {
  name: 'ArticleNavComponent',
  data() {
    return {
      dropdownContents: ['리뷰게시판', '자유게시판'],
      communityCategories: ['전체', '공지'],
      performGenres: ['뮤지컬', '연극', '클래식', '아동', '복합'],
    }
  },
  methods: {
    ...mapActions(useCommunities, ['searchArticles']),
  },
}
</script>

<style scoped>
.nav-line{
  margin: 0px 0px 40px 0px;
  border-top: 3px solid;
  border-color: #1B3C33;
}
.Community {
  margin: 0;
  padding: 0;
}
.dropbtn {
  background-color: #F9F9F9;
  color: #1B3C33;
  padding: 16px;
  font-size: 16px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  width: 88px;
  height: 48px;
  text-align: center;
  align-content: center;
}

.dropdown:hover .dropbtn {
  background-color: #1B3C33;
  color: white;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: #f1f1f1
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>