import { defineStore } from 'pinia'
import axios from 'axios'
import secosi from "@/api/secosi"

export const usePerforms = defineStore('performs', {
  state: () => {
    return {
      performs: [],
    }
  },
  persist: true,
  computed: {
  },
  actions: {
    fetchPerforms() {
      axios.get(secosi.performs.performs())
        .then(res => {
          this.performs=res.data
        })
    },
  },
})