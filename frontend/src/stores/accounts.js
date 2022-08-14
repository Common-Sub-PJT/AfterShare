import { defineStore } from 'pinia'
// import router from "@/router";
import secosi from "@/api/secosi";
import axios from "axios";

export const useAccounts = defineStore({
  id: 'accounts',
  state: () => ({
    token: localStorage.getItem('token') || '' ,
    currentUser: {},
    profile: {},
    authError: null,
  }),
  getters: {
    isLoggedIn: state => !!state.token,
    authHeader: state => ({ Authorization: `Token ${state.token}`}),
  },
  actions: {
    removeToken() {
      localStorage.setItem('token', '')
      this.currentUser = {}
      window.location.reload()
    },
    fetchCurrentUser() {
      if (this.isLoggedIn) {
        axios.get(secosi.accounts.currentUserInfo(), {
          headers: {
            Authorization : `Bearer ${this.token}`
          }
        })
          .then(res => {
            this.currentUser = res.data
          })
          .catch(err => {
            if (err.response.status === 401) {
              this.removeToken()
            }
          })
        }
      },
      login(credential) {
        axios.post(secosi.accounts.login(), credential)
        .then(res => {
          localStorage.setItem('token', res.data.accessToken)
          this.token = res.data.accessToken
          this.fetchCurrentUser()
        })
        .catch(err => {
          console.error(err.response.data)
        })
    },
    signup(credential) {
      axios.post(secosi.accounts.signup(), credential)
        .then(res => {
        })
        .catch(err => {
          console.error(err.response.data)
        })
      },
  }
})
