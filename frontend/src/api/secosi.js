const HOST = "http://localhost:8080/api/"
// const HOST = "https://i7b303.p.ssafy.io/api/"
const ACCOUNTS = "users"
const COMMUNITIES = "communities"
const PERFORMS = 'performs'
const CONFERENCES = "conferences"
const MAIN = "main"
const MAIL = "mail"

export default {
  accounts: {
    login: () => HOST + "auth/login",
    signup: () => HOST + ACCOUNTS ,
    currentUserInfo: () => HOST + ACCOUNTS + '/profile',
    userIdCheck: () => HOST + ACCOUNTS + '/check-userid',
    emailCheck: () => HOST + ACCOUNTS + '/check-email',
    nicknameCheck: () => HOST + ACCOUNTS + '/check-name',
    deleteAccount: userId => HOST + ACCOUNTS + `/${userId}`
  },
  mypages: {
    myArticles: userId => HOST + ACCOUNTS + `/${userId}` + '/my-articles',
    myComments: userId => HOST + ACCOUNTS + `/${userId}` + '/my-comments',
    myFollowers: userId => HOST + ACCOUNTS + '/follower' + `/${userId}`,
    myFollowings: userId => HOST + ACCOUNTS + '/following' + `/${userId}`,
    passwordChange: () => HOST + ACCOUNTS + '/change-password',
    editProfile: userId => HOST + ACCOUNTS + `/${userId}`,
    deleteAccount: userId => HOST + ACCOUNTS + `/${userId}`,
  },
  communities: {
    community: () => HOST + COMMUNITIES,
    search: searchInfo => HOST + COMMUNITIES + `/${searchInfo}`,
    articleDetail: articleId => HOST + COMMUNITIES + `/${articleId}`,
    articleUpdate: articleId => HOST + COMMUNITIES + `/${articleId}`,
    comment: articleId => HOST + COMMUNITIES + `/${articleId}` + '/comments',
    commentEdit: (articleId, commentId) => HOST + COMMUNITIES + `/${articleId}` + '/comments' + `/${commentId}`,
    viewCount: articleId => HOST + COMMUNITIES + `/${articleId}` + '/count',
    like: articleId => HOST + COMMUNITIES + `/${articleId}` + '/like',
    articleDelete: (articleId) => HOST + COMMUNITIES + `/${articleId}`,
    commentDelete: (articleId, commentId) => HOST + COMMUNITIES + `/${articleId}` + '/comments' + `/${commentId}`,
    articleSearch: (articleGenre, articleCategory, searchCategory, searchWord) => HOST + COMMUNITIES + `/${articleGenre}` + `/${articleCategory}` + `/${searchCategory}` + `/${searchWord}`,
  },
  performs: {
    performs: () => HOST + PERFORMS,
    guguns: () => HOST + PERFORMS + '/guguns',
    perform: mt20id => HOST + PERFORMS + `/${mt20id}`,
    search: () => HOST + PERFORMS + '/search',
  },
  conferences: {
    conferences: () => HOST + CONFERENCES,
    search: () => HOST + CONFERENCES + '/search',
    create: () => HOST + CONFERENCES +'/chat',
    follow: () => HOST + CONFERENCES + '/follow',
    unfollow: () => HOST + CONFERENCES + '/unfollowing',
    join: () => HOST + CONFERENCES + '/roomIn',
    out: () => HOST + CONFERENCES + '/roomOut',
    following: () => HOST + CONFERENCES + '/following',
    report: () => HOST + CONFERENCES + '/report',
  },
  main: {
    prfrank: () => HOST + MAIN + '/performs' + '/ranking',
    cfrrank: () => HOST + MAIN + '/conferences' + '/ranking',
    artrank: () => HOST + MAIN + '/communities' + '/ranking',
  },
  mail:{
    passwordFind:() => HOST + MAIL + '/findmy/sendemail'
  }
}