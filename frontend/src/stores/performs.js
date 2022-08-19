import { defineStore } from 'pinia'
import axios from 'axios'
import secosi from "@/api/secosi"

export const usePerforms = defineStore('performs', {
  state: () => {
    return {
      performList: [],
    }
  },
  persist: true,
  computed: {
  },
  actions: {
    fetchPerforms() {
      axios.get(secosi.performs.performs())
        .then(res => {
          this.performList=res.data
        })
    },
  },
})