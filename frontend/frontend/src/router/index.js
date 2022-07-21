import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/home/Home.vue')
    },
    {
      path: '/conferencelist',
      name: 'conferencelist',
      component: () => import('@/views/conferencelist/ConferenceListView.vue')
    },
    {
      path: '/conference',
      name: 'conference',
      component: () => import('@/views/conference/ConferenceView.vue')
    },
    {
      path: '/conferencecreate',
      name: 'conferencecreate',
      component: () => import('@/views/conferencecreate/ConferenceCreateView.vue')
    },
    {
      path: '/history',
      name: 'history',
      component: () => import('@/views/history/HistoryView.vue')
    },
    {
      path: '/community',
      name: 'community',
      component: () => import('@/views/community/CommunityView.vue')
    },
    {
      path: '/communitydetail',
      name: 'communitydetail',
      component: () => import('@/views/communitydetail/CommunityDetailView.vue')
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: () => import('@/views/mypage/MypageView.vue')
    },
    {
      path: '/perform',
      name: 'perform',
      component: () => import('@/views/perform/PerformView.vue')
    },
    {
      path: '/performdetail',
      name: 'performdetail',
      component: () => import('@/views/performdetail/PerformDetailView.vue')
    },
    {
      path: '/manage',
      name: 'manage',
      component: () => import('@/views/manage/ManageView.vue')
    },
    {
      path: '/manage',
      name: 'manage',
      component: () => import('@/views/manage/ManageView.vue')
    },
  ]
})

export default router
