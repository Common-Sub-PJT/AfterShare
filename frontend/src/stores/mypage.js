import { defineStore } from 'pinia'
import axios from 'axios'
import secosi from "@/api/secosi"

export const useMypages = defineStore('mypages', {
  state: () => {
    return {
      myArticleList: [],
      myCommentList: [],
    }
  },
  persist: true,
  computed: {
  },
  actions: {
    getMyArticles(userId) {
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      axios.get(secosi.mypages.myArticles(userId), config)
      .then(res => {
        this.myArticleList = res.data
      })
      .catch(err => {
      })
    },
    getMyComments(userId) {
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      axios.get(secosi.mypages.myComments(userId), config)
      .then(res => {
        this.myCommentList = res.data
      })
      .catch(err => {
      })
    },
  }
})