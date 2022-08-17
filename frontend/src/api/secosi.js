<<<<<<< HEAD
//const HOST = "https://i7b303.p.ssafy.io/api/"
const HOST = "http://localhost:8080/api"
=======
<<<<<<< HEAD
const HOST = "https://i7b303.p.ssafy.io/api/"
// const HOST = "http://localhost:8080/api/"
=======
<<<<<<< HEAD
const HOST = "https://i7b303.p.ssafy.io/api/"
// const HOST = "http://localhost:8080/api"
=======
const HOST = "http://localhost:8080/api/"
// const HOST = "https://i7b303.p.ssafy.io/api/"
>>>>>>> 45e50df21c4ea6e918272167759fb754d1ab2cf1
>>>>>>> 4919ad605eb38b9d817c0e40b5d2abcdfd8a3d2c
>>>>>>> 2376c12215ab30dbc265962b37f216c6442d9a33
const ACCOUNTS = "users"
const COMMUNITIES = "communities"
const PERFORMS = 'performs'
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
    guguns: () => HOST + PERFORMS + '/guguns',
    perform: mt20id => HOST + PERFORMS + `/${mt20id}`,
    search: () => HOST + PERFORMS + '/search',
  },
  conferences: {
    conferences: () => HOST + CONFERENCES,
    search: () => HOST + CONFERENCES + '/search',
    create: () => HOST + CONFERENCES +'/chat'
  },
}
