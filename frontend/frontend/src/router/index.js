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
      path: '/login',
      name: 'login',
      component: () => import('@/views/account/LoginItem.vue')
    },
    {
      path: '/signup',
      name: 'signup',
      component:  () => import('@/views/account/SignupItem.vue')
    },
    {
      path: '/logout',
      name: 'logout',
      component:  () => import('@/views/account/logoutItem.vue')
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

    // community 말고 articles로 코드 짜는 것 community는 communitypk일 때만 사용

    // {
    //   path: '/community',
    //   name: 'community',
    //   component: () => import('@/views/community/CommunityView.vue')
    // },
    // {
    //   path: '/community/new',
    //   name: 'communityNew',
    //   component: () => import('@/views/community/CommunityCreateView.vue')
    // },
    // {
    //   path: '/community/:communityPk', //커뮤니티 별 게시물들 불러오는 것.
    //   name: 'communityArticles',
    //   component: () => import('@/views/community/CommunityView.vue')
    // },
    // {
    //   path: '/community/:articlePk/detail',
    //   name: 'communitydetail',
    //   component: () => import('@/views/communitydetail/CommunityDetailView.vue')
    // },
    {
      path: '/mypage/:username',
      name: 'mypage',
      component: () => import('@/views/mypage/MypageView.vue')
    },

    // 일단 보류
    // 전체 
    // {
    //   path: '/perform',
    //   name: 'perform',
    //   component: () => import('@/views/perform/PerformView.vue')
    // },
    // {
    //   path: '/performdetail',
    //   name: 'performdetail',
    //   component: () => import('@/views/performdetail/PerformDetailView.vue')
    // },

    
    {
      path: '/manage',
      name: 'manage',
      component: () => import('@/views/manage/ManageView.vue')
    },
    {
      path: '/manage/report',
      name: 'managereport',
      component: () => import('@/views/manage/ManageReportView.vue')
    },

    // 신고를 했을 때 백엔드로 보내줄 router 필요.

  ]
})

export default router
