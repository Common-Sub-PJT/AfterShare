import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import './assets/main.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')

import InfiniteLoading from "v3-infinite-loading"
import "v3-infinite-loading/lib/style.css"
app.component("infinite-loading", InfiniteLoading)