import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/home/Home.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/account/LoginView.vue')
    },
    {
      path: '/signup',
      name: 'Signup',
      component:  () => import('@/views/account/SignupView.vue')
    },
    {
      path: '/logout',
      name: 'Logout',
      component:  () => import('@/views/account/logoutView.vue')
    },
    {
      path: '/conferencelist',
      name: 'ConferenceList',
      component: () => import('@/views/conferencelist/ConferenceListView.vue')
    },
    {
      path: '/conference/:conferenceId',
      name: 'ConferenceDetail',
      component: () => import('@/views/conference/ConferenceDetailView.vue')
    },
    {
      path: '/conferencecreate',
      name: 'conferenceCreate',
      component: () => import('@/views/conferencecreate/ConferenceCreateView.vue')
    },
    {
      path: '/history/:userId',
      name: 'History',
      component: () => import('@/views/history/HistoryView.vue')
    },

    {
      path: '/articles', //전체 article목록
      name: 'Articles',
      component: () => import('@/views/articles/ArticlesView.vue')
    },
    {
      path: '/articles/create', //새 글 작성
      name: 'ArticleCreate',
      component: () => import('@/views/articles/ArticlesCreateView.vue')
    },
    {
      path: '/articles/:communityId', // 커뮤니티 별 게시물들 불러오는 것.
      name: 'CommunityArticles',
      component: () => import('@/views/articles/CommunityArticlesView.vue')
    },
    {
      path: '/articles/:articlePk/detail',
      name: 'ArticleDetail',
      component: () => import('@/views/articles/ArticlesDetailView.vue')
    },
    {
      path: '/articles/:articlePk/edit', //delete랑 edit 같이
      name: 'ArticleEdit',
      component: () => import('@/views/articles/ArticlesEditView.vue')
    },
    {
      path: '/mypage/:username',
      name: 'Mypage',
      component: () => import('@/views/mypage/MypageView.vue')
    },
    {
      path: '/perform',
      name: 'Perform',
      component: () => import('@/views/perform/PerformView.vue')
    },
    {
      path: '/perform/:performId',
      name: 'PerformDetail',
      component: () => import('@/views/perform/PerformDetailView.vue')
    },
    {
      path: '/perform/:performId/search',
      name: 'PerformSearch',
      component: () => import('@/views/perform/PerformSearchView.vue')
    },
    {
      path: '/manage', //관리자 페이지의 메인이고 여기서 신고된 모든 유저 정보를 확인할 수 있음
      name: 'Manage',
      component: () => import('@/views/manage/ManageView.vue')
    },
    {
      path: '/manage/report', // 신고가 들어온 사유 리스트 
      name: 'ManageReport',
      component: () => import('@/views/manage/ManageReportView.vue')
    },
    // 신고를 했을 때 백엔드로 보내줄 router 필요.
    {
      path: '/report/:nickname', // 신고를 당한 사람 닉네임 
      name: 'Report',
      component: () => import('@/views/manage/ReportView.vue')
    },
  ]
})

export default router
