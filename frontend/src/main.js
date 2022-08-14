import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import './assets/main.css'
import 'v-calendar/dist/style.css';
import { SetupCalendar } from 'v-calendar'

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)
app.use(SetupCalendar, {})

app.mount('#app')

import InfiniteLoading from "v3-infinite-loading"
import "v3-infinite-loading/lib/style.css"
app.component("infinite-loading", InfiniteLoading)