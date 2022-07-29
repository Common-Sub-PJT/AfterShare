import { defineStore } from 'pinia'

export const useCounter2Store = defineStore({
  id: 'counter2',
  state: () => ({
    counter2: 0
  }),
  getters: {
    doubleCount2: (state) => state.counter2 * 2
  },
  actions: {
    increment() {
      this.counter2 = this.counter2 + 2
    }
  }
})
