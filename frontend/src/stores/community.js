import { defineStore } from 'pinia'
import axios from 'axios'
import secosi from "@/api/secosi";

export const useCommunities = defineStore('communities', {
  state: () => {
    return {
      articleListName: '전체게시판',
      articleList: [],
      articleGenre: 0,
      articleCategory: 0,
      commentList: [],
      commentListRerendering: 0,
      genre: '전체',
      category: '전체',
    }
  },
  persist: true,
  getters: {

  },
  actions: {
    updateArticleList(newArticleList) {
      this.articleList = newArticleList
    },
    searchArticles(genre, category) {
      this.genre = genre
      this.category = category

      let searchInfo = ''
      const genreMatch = {
        '자유게시판': 0,
        '리뷰게시판': 1,
        '뮤지컬': 2,
        '연극': 3,
        '클래식': 4,
        '아동': 5,
        '복합': 6,
      }

      if (genre === '전체') {
        this.articleListName = '전체게시판'
        searchInfo = 'all'
        this.articleGenre = 0
        this.articleCategory = 0
      } else if (genre === '공지') {
        this.articleListName = '공지사항'
        searchInfo = '1/1'
        this.articleGenre = 1
        this.articleCategory = 1
      } else {
        this.articleListName = '' + genre + ' - ' + category
        searchInfo = genreMatch[genre] + '/' + genreMatch[category]
        this.articleGenre = genreMatch[genre]
        this.articleCategory = genreMatch[category]
      }
      axios.get(secosi.communities.search(searchInfo))
      .then(res => {
        this.articleList = res.data
      })
      .catch(err => {
      })
    },
    searchComments(articleId) {
      axios.get(secosi.communities.comment(articleId))
      .then(res => {
        this.commentList = res.data
      })
      .catch(err => {
      })
    }
  }
})

// 전체 목록에서 장르/카테고리
// 전체 : 0, 0
// 공지 : 1/0,1
// 뮤지컬 : 2/0,1
// 연극 : 3/0,1
// 클래식 : 4/0,1
// 아동 : 5/0,1
// 복합 : 6/0,1
// --------------------------------------
// 자유 : 0
// 리뷰 : 1