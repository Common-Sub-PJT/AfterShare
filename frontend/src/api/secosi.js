const HOST = "http://localhost:8080/api/"
// const HOST = "https://i7b303.p.ssafy.io/api/"
const ACCOUNTS = "users"
const COMMUNITIES = "communities"
const PERFORMS = 'performs/'
const CONFERENCES = "conferences"

export default {
  accounts: {
    login: () => HOST + "auth/login",
    signup: () => HOST + ACCOUNTS ,
    currentUserInfo: () => HOST + ACCOUNTS + '/profile',
    userIdCheck: () => HOST + ACCOUNTS + '/check-userid',
    emailCheck: () => HOST + ACCOUNTS + '/check-email',
    nicknameCheck: () => HOST + ACCOUNTS + '/check-name',
  },
  communities: {
    community: () => HOST + COMMUNITIES,
    search: searchInfo => HOST + COMMUNITIES + `/${searchInfo}`,
    articleDetail: articleId => HOST + COMMUNITIES + `/${articleId}`,
    articleUpdate: articleId => HOST + COMMUNITIES + `/${articleId}`,
    comment: articleId => HOST + COMMUNITIES + `/${articleId}` + '/comments',
    commentEdit: (articleId, commentId) => HOST + COMMUNITIES + `/${articleId}` + '/comments' + `/${commentId}`,
    viewCount: articleId => HOST + COMMUNITIES + `/${articleId}` + '/count',
    articleDelete: (articleId) => HOST + COMMUNITIES + `/${articleId}`,
    commentDelete: (articleId, commentId) => HOST + COMMUNITIES + `/${articleId}` + '/comments' + `/${commentId}`,
    articleSearch: (articleGenre, articleCategory, searchCategory, searchWord) => HOST + COMMUNITIES + `/${articleGenre}` + `/${articleCategory}` + `/${searchCategory}` + `/${searchWord}`,
  },
  performs: {
    performs: () => HOST + PERFORMS,
    guguns: () => HOST + PERFORMS + 'guguns',
    perform: mt20id => HOST + PERFORMS + `${mt20id}`,
  },
  conferences: {
    conferences: () => HOST + CONFERENCES,
    search: () => HOST + CONFERENCES + '/search',
  },
}