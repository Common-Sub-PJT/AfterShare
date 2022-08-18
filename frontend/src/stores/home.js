import { defineStore } from 'pinia'
import axios from 'axios'
import secosi from "@/api/secosi"

export const useHomes = defineStore('homes', {
  state: () => {
    return {
      performRank: {
        all: [],
        act: [],
        musical: [],
        dance: [],
        classic: [],
        opera: [],
        gukak: [],
        complex: [],
      },
    }
  },
  persist: true,
  computed: {
  },
  actions: {
    fetchPerformRank(code, data) {
      if (code === "AAAA") {
        this.performRank.act = data
      }
      else if (code === "AAAB") {
        this.performRank.musical = data
      }
      else if (code === "BBBA") {
        this.performRank.dance = data
      }
      else if (code === "CCCA") {
        this.performRank.classic = data
      }
      else if (code === "CCCB") {
        this.performRank.opera = data
      }
      else if (code === "CCCC") {
        this.performRank.gukak = data
      }
      else if (code === "EEEA") {
        this.performRank.complex = data
      }
    },
  fetchPerformAllRank() {
    axios.get(secosi.main.prfrank(), { params: { "catecode" : "" } })
      .then(res => {
        this.performRank.all = res.data
      })
    },
  fetchPerformGenreRank() {
    const genreList = [
      { code: "AAAA" },
      { code: "AAAB" },
      { code: "BBBA" },
      { code: "CCCA" },
      { code: "CCCB" },
      { code: "CCCC" },
      { code: "EEEA" },
    ]
       
  genreList.forEach(el => {
    axios.get(secosi.main.prfrank(), { params: { "catecode" : el.code } })
      .then(res => {
        if (el.code === "AAAA") {
          this.performRank.act = res.data
        }
        else if (el.code === "AAAB") {
          this.performRank.musical = res.data
        }
        else if (el.code === "BBBA") {
          this.performRank.dance = res.data
        }
        else if (el.code === "CCCA") {
          this.performRank.classic = res.data
        }
        else if (el.code === "CCCB") {
          this.performRank.opera = res.data
        }
        else if (el.code === "CCCC") {
          this.performRank.gukak = res.data
        }
        else if (el.code === "EEEA") {
          this.performRank.complex = res.data
        }
      })
      })
    },
  },
})