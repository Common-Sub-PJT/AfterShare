<template>
  <div>
    <div class="profile-big-box">
      <div class="profile-small-box">
        <div @click="ShowProfileImageComponent" class="profile-image-box" :key="`${this.currentUser.profile_img}`">
          <img v-if="this.currentUser.profile_img" @click="ShowProfileImageComponent" class="profile-image" :src="`./../src/assets/account/${this.currentUser.profile_img}.png`">             
        </div>
        <img class="edit_img" src="@/assets/folder.png">
        <div class="profile-detail">
          <div class="profile-detail-username">
            <input v-if="this.isEdit" v-model="newUsername" @keyup="usernameValid" type="text" class="modal-input" id="newUsername" name="newUsername" :placeholder="this.currentUser.name">
            <p class="username-style" v-else :key="this.currentUser.name">닉네임 : {{ this.currentUser.name }}</p>
          </div>
          <div class="profile-detail-aboutme">
            <input v-if="this.isEdit" v-model="newAboutMe" type="text" class="modal-input" id="newAboutMe" name="newAboutMe" :placeholder="this.currentUser.about_me">
            <p class="username-style" v-else>한 줄 소개 : {{ this.currentUser.about_me }}</p>
          </div>
          <div class="profile-detail-follow">
            <button @click="getFollowings">팔로잉</button>
            <p class="mypage-follow-cnt">{{ this.currentUser.following_cnt }}</p>
            <button @click="getFollowers">팔로워</button>
            <p class="mypage-follow-cnt">{{ this.currentUser.follower_cnt }}</p>
          </div>
          <div class="profile-detail-setting">
            <button>설정</button>
            <button v-if="!this.isEdit" @click="editProfileOn">프로필수정</button>
            <button v-if="this.isEdit" @click="editProfile(), editProfileOff()">수정</button>
            <button v-if="this.isEdit" @click="editProfileOff">취소</button>
            <button @click="ShowPasswordChangeModal">비밀번호 변경</button>
            <button @click="DeleteAccount">회원탈퇴</button>
            <button @click="Unfollow">언팔로우</button>
          </div>
        </div>
      </div>
    </div>
    <div class="nav-line"></div>
    <div class="mytext-box">
      <div>
        <h3 class="text-center">나의 게시글</h3>
        <div class="my-article-box">
          <article-item></article-item>
        </div>
      </div>
      <!-- <hr class="verticle-line"> -->
      <div class="verticle-line"></div>
      <div>
        <h3 class="text-center">나의 댓글</h3>
        <div class="my-comment-box">
          <comment-item></comment-item>
        </div>
      </div>
    </div>
  </div>
  <PasswordChangeComponent v-show="isPasswordChangeViewVisible" @PasswordChangeClose="ClosePasswordChangeModal">
  </PasswordChangeComponent>
  <ProfileImageComponent v-show="isProfileImageComponentVisible" @ProfileImageComponentClose="CloseProfileImageComponent">
  </ProfileImageComponent>
</template>

<script>
import axios from 'axios'
import secosi from '@/api/secosi.js'
import ArticleItem from '@/views/mypage/components/ArticleItem.vue'
import CommentItem from '@/views/mypage/components/CommentItem.vue'
import PasswordChangeComponent from '@/views/mypage/components/PasswordChangeComponent.vue'
import ProfileImageComponent from '@/views/mypage/components/ProfileImageComponent.vue'
import { mapState, mapActions } from 'pinia'
import { useAccounts } from '@/stores/accounts.js'

export default {
  name: "MypageView",
  data() {
    return {
      isPasswordChangeViewVisible: false,
      isProfileImageComponentVisible: false,
      userId: '',
      profilePhoto: '',
      isEdit: false,
      newUsername: '',
      newAboutMe: '',
    }
  },
  methods: {
    ...mapActions(useAccounts, ['removeToken', 'fetchCurrentUser']),
    editProfile() {
      const config = {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        }
      const context = {
        "aboutMe": this.newAboutMe,
        "profileImg": this.currentUser.profile_img,
        "userEmail": this.currentUser.email,
        "userName": this.newUsername,
      }
      axios.patch(secosi.mypages.editProfile(this.currentUser.userId), context, config)
      .then(res => {
        this.fetchCurrentUser()
      })
      .catch(err => {
      })
    },
    getFollowers() {
      axios.get(secosi.mypages.myFollowers(this.userId))
      .then(res => {
        console.log(res.data)
      })
      .catch(err => {
      })
    },
    editProfileOn() {
      this.isEdit = true
    },
    editProfileOff() {
      this.isEdit = false
    },
    getFollowings() {
      axios.get(secosi.mypages.myFollowings(this.userId))
      .then(res => {
        console.log(res.data)
      })
      .catch(err => {
      })
    },
    ShowPasswordChangeModal() {
      this.isPasswordChangeViewVisible = true;
    },
    ClosePasswordChangeModal() {
      this.isPasswordChangeViewVisible = false;
    },
    ShowProfileImageComponent() {
      this.isProfileImageComponentVisible = true;
    },
    CloseProfileImageComponent() {
      this.isProfileImageComponentVisible = false;
    },
    DeleteAccount() {
      axios.delete(secosi.accounts.DeleteAccount(this.userId))
      .then(res => {
        this.removeToken()
      })
      .catch(err => {
      })
    },
    Unfollow() {
      const params = new URLSearchParams();
      params.append('unfollowingId', 'rkd9755');
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
      console.log(config)

      // axios.delete(secosi.conferences.unfollow(), {params: {'unfollowingId' : 'rkd9755',}}, config)
      // .then(res => {
      //   console.log('complete')
      // })
      // .catch(err => {
      // })

      axios
      .delete(secosi.conferences.unfollow(), {
        params: {
          'unfollowingId': 'rkd9755',
          'userId': this.currentUser.userId,
        }
      }, {
        headers: {
          Authorization: "Bearer ${token}",
          token: localStorage.getItem("token")
        }
      })
      .then(res => {
        console.log(res);
      });
    },
  },
  computed: {
    ...mapState(useAccounts, ['currentUser']),
  },
  components: {
    ArticleItem,
    CommentItem,
    PasswordChangeComponent,
    ProfileImageComponent,
  },
  created() {
    this.username = this.currentUser.name
    this.aboutMe = this.currentUser.aboutMe || '-'
    this.userId = this.currentUser.userId
    console.log(this.currentUser)
  }
}
</script>

<style scoped>
.username-style {
  font-weight: bold;
  font-size: 16px;
  margin:0;
}
.profile-image {
  width: 120px;
  height: 120px;
}
.verticle-line {
  border-left: 1px dashed;
  border-color: #1B3C33;
}
.nav-line{
  margin: 10px 0px 40px 0px;
  border-top: 3px solid;
  border-color: #1B3C33;
}
.my-comment-box {
  border: 3px solid;
  border-color: #1B3C33;
}
.my-article-box {
  border: 3px solid;
  border-radius: 5px;
  border-color: #1B3C33;
}

.profile-detail-follow {
  display: flex;
  border-color: #1B3C33;
  align-items : center;
}
.profile-detail-username {
  border: 1px solid;
  border-radius: 10px;
  padding: 10px;
  border-color: #1B3C33;
  margin: 5px;
}
.profile-detail-aboutme {
  border: 1px solid;
  border-radius: 10px;
  margin :5px;
  padding: 10px;
  border-color: #1B3C33;
}
.profile-detail-setting {
  border-color: #1B3C33;
}
.profile-detail {
  grid-row: span 9;
  border-color: #1B3C33;
  padding-left : 20px;
  padding-right : 20px;
  padding-bottom : 20px;
  margin-left: 30px;
  border-style: none;
  margin : 5px;
  
}
.photo-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.profile-image-box {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
  background: #BDBDBD;
  position: relative;
  padding: 15px;
  display: flex;
  justify-content: center;
  align-content: center;
  grid-row: span 3;
}
.profile-small-box {
  margin: 60px 0px 20px 0px;
  grid-row: auto;
  display: flex;
  width: 820px;
  height: 280px;
  border: 3px solid;
  border-radius: 15px;
  padding : 30px;
  border-color: #1B3C33;
}

.profile-big-box {
  display: flex;
  justify-content: center;
}

.mytext-box {
  display: flex;
  justify-content: space-around;
}

.mypage-follow-cnt{
  align-items : center;
  margin: 0px;
  padding : 10px;
}

.edit_img{
  z-index: 0;
  width: 35px;
  height: 30px;
  left: 110px;
  margin-left: -35px;
  margin-top: 110px;
  border-radius:3px;
  padding: 1px 3px 3px 5px;
  background-color: #1B3C33;
}

button{
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border-radius: 5px;
  color: white;
  border-style: none;
  margin: 5px;
  background-color: #1B3C33;
}
</style>