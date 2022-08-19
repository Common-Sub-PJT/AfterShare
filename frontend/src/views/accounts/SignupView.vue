<template>
  <div class="modal" id="email-modal">
    <div class="modal-content">
      <div class="modal-content-left">
        <img  id="modal-img" src="@/assets/test.png" alt="모달이미지">
      </div>
      <div class="modal-content-right">
        <div  @click="$emit('signupClose')" class="close-btn">&times;</div>
        <!-- <form id="form" @submit.prevent="[signupValid(), $emit('signupClose'), $emit('showLogin')]" class="modal-form"> -->
        <form id="form" @submit.prevent="signupValid" class="modal-form">
          <h1 class="font-weight-bold">회원가입!</h1>
          <div class="form-validation">
            <input v-model="userId" @keyup="userIdValid" type="text" class="modal-input" name="userId" id="userId" placeholder="아이디를 입력하세요" required>
            <p class="userId-check">UserId Valid Error</p>
          </div>
          <div class="form-validation">
            <input v-model="email" @keyup="emailValid" type="email" class="modal-input" id="email" name="email" placeholder="이메일을 입력하세요">
            <p class="email-check">Email Valid Error</p>
          </div>
          <div class="form-validation">
            <input v-model="nickname" @keyup="nicknameValid" type="text" class="modal-input" id="nickname" name="nickname" placeholder="닉네임을 입력하세요">
            <p class="nickname-check">Nickname Valid Error</p>
          </div>
          <div class="form-validation">
            <input v-model="password" @keyup="pwValid(), pwCheckValid()" type="password" class="modal-input" id="password" name="password" placeholder="비밀번호를 입력하세요">
            <p class="password-check">Password Valid Error</p>
          </div>
          <div class="form-validation">
            <input v-model="passwordCheck" @keyup="pwCheckValid" type="password" class="modal-input" id="password-confirm" name="password" placeholder="비밀번호 재입력">
            <p class="passwordCheck-check">Password-Check Valid Error</p>
          </div>
          <input type="submit" class="modal-input-btn" value="Sign up">
          <div @click="[$emit('showLogin'), $emit('signupClose')]" style="border: none; cursor: pointer;" class="my-2">로그인</div>
        </form>
      </div>
    </div>
  </div> 
</template>

<script>
// import $ from 'jquery'
import { defineComponent } from "vue";
import { useAccounts } from "@/stores/accounts";
import secosi from "@/api/secosi";
import axios from "axios";
// import { ref, computed } from "vue";

export default defineComponent ({
  name: 'SignupView',
  data() {
    return {
      errors:[],
      userId: '',
      email: '',
      nickname: '',
      password: '',
      passwordCheck: '',
      signupPermission: false,
    }
  },
  computed: {
  },
  setup() {
    const signupValid = function signupValid() {
      const signup = useAccounts()
      const valueCheck = this.userId.length * this.email.length * this.nickname.length * this.password.length
      if (valueCheck > 0 && this.signupPermission) {
        const credential = {
          "email": this.email,
          "name": this.nickname,
          "password": this.password,
          "userId": this.userId
        }
        signup.signup(credential)
        this.email = ''
        this.nickname = ''
        this.password = ''
        this.userId = ''
        this.passwordCheck = ''
        this.$emit('signupClose')
        this.$emit('showLogin')
      }
    }

    const userIdValid = function userIdValid() {
      const userId = document.querySelector('.userId-check')
      if (this.userId.length < 3 || this.userId.length > 30) {
        userId.style.display = 'block'
        userId.innerText = '아이디는 3~30 글자여야 합니다'
      } else {
        axios.get(secosi.accounts.userIdCheck() + '/' + this.userId)
          .then(res => {
            if (res.data.message) {
              userId.style.display = 'none'
              this.signupPermission ||= true
            }
          })
          .catch(err => {
            this.signupPermission &&= false
            if (err.response.data.statusCode === 409) {
              userId.style.display = 'block'
              userId.innerText = '중복되는 아이디입니다'
            } else {
              userId.style.display = 'block'
              userId.innerText = 'Invalid userId'
            }
          })
      }
    }
    const emailValid = function emailValid() {
      const email = document.querySelector('.email-check')
      if (this.email.length < 1) {
        email.style.display = 'block'
        email.innerText = '이메일을 입력해주세요'
        this.signupPermission &&= false
      } else {
        axios.get(secosi.accounts.emailCheck() + '/' + this.email)
          .then(res => {
            if (res.data.message) {
              email.style.display = 'none'
              this.signupPermission ||= true
            }
          })
          .catch(err => {
            this.signupPermission &&= false
            if (err.response.data.statusCode === 409) {
              email.style.display = 'block'
              email.innerText = '중복되는 이메일입니다'
            } else {
              email.style.display = 'block'
              email.innerText = 'Invalid email'
            }
          })
      }
    }
    const nicknameValid = function nicknameValid() {
      const nickname = document.querySelector('.nickname-check')
      if (this.nickname.length < 3 || this.nickname.length > 20) {
        nickname.style.display = 'block'
        nickname.innerText = '닉네임은 3~20 글자여야 합니다'
        this.signupPermission &&= false
      } else {
        axios.get(secosi.accounts.nicknameCheck() + '/' + this.nickname)
          .then(res => {
            if (res.data.message) {
              nickname.style.display = 'none'
              this.signupPermission ||= true
            }
          })
          .catch(err => {
            this.signupPermission &&= false
            if (err.response.data.statusCode === 409) {
              nickname.style.display = 'block'
              nickname.innerText = '중복되는 닉네임입니다'
            } else {
              nickname.style.display = 'block'
              nickname.innerText = 'Invalid nickname'
            }
          })
      }
    }
    const pwValid = function passwordValid() {
      const password = document.querySelector('.password-check')
      const pattern1 = /[0-9]/
	    const pattern2 = /[a-zA-Z]/
	    const pattern3 = /[~!@#$%^&*()_+|<>?:{}]/
      if (!pattern1.test(this.password) || !pattern2.test(this.password) || !pattern3.test(this.password) || this.password.length < 8) {
        password.style.display = 'block'
        password.innerText = '비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성되어야 합니다'
        this.signupPermission &&= false
      } else {
        password.style.display = 'none'
        this.signupPermission ||= true
      }
    }
    const pwCheckValid = function passwordCheckValid() {
      const passwordCheck = document.querySelector('.passwordCheck-check')
      if (this.password === this.passwordCheck) {
        passwordCheck.style.display = 'none'
        this.signupPermission ||= true
      } else {
        passwordCheck.style.display = 'block'
        passwordCheck.innerText = '비밀번호와 일치하지 않습니다'
        this.signupPermission &&= false
      }
    }

    return{
      signupValid,
      userIdValid,
      emailValid,
      nicknameValid,
      pwValid,
      pwCheckValid,
    }
  }
})
</script>

<style scoped>
.userId-check, .email-check, .nickname-check, .password-check , .passwordCheck-check{
  font-size: 0.7rem;
  margin-top: 0.5rem;
  margin-bottom: 0rem;
  margin-left: 0.5rem;
  color: #f00e0e;
  top: 1.8rem;
  display: none;
}
.modal {
  position: fixed;
  z-index: 10;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
  display: block;
}
.modal-content{
  max-width: 800px;
  background: #f4f4f4;
  margin: 10% auto;
  width: 60%;
  box-shadow: 0 5px 8px 0 rgba(0, 0, 0, 0.2), 0 7px 20px 0 rgba(0, 0, 0, 0.2);
  position: relative;
  border-radius: 3px;
  min-height: 500px;
  display: grid;
  grid-template-columns: 1fr 1fr;
}
.close-btn {
  position: absolute;
  top: 2%;
  right: 3%;
  font-size: 1.5rem;
  cursor: pointer;
}
.modal-content-left {
  background: linear-gradient(90deg, black 0%, black 100%);
  border-radius: 0 3px 3px 0;
  position: relative;
}
#modal-img {
  width: 70%;
  height: auto;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.modal-content-right {
  border-radius: 0 3px 3px 0;
  position: relative;
}
.modal-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  height: 83%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.modal-form h1 {
  font-size: 1rem;
  text-align: start;
  font-size: 1.5rem;
  width: 80%;
  margin-bottom: 1rem;
}
.form-validation {
  margin-bottom: 0.5rem;
  width: 80%;
}
/* .form-validation.error {
  margin-bottom: 1.5rem;
  position:relative;
  border: 1px solid #f00e0e;
  border-radius: 2px;
}
.form-validation.valid {
  margin-bottom: 0.5rem;
  position: relative;
  border: 1px solid #1B3C33;
  border-radius: 2px;
}
.form-validation p{
  font-size: 0.7rem;
  margin-top: 0.5rem;
  color: #f00e0e;
  position: absolute;
  top: 1.8rem;
  display: none;
} */
/* .form-validation.error p {
  display: block;
} */
.modal-input {
  display: block;
  padding-left: 0.5rem;
  outline: none;
  border: none;
  height: 2rem;
  width: 100%;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.05);
}
.modal-input::placeholder{
  color: #b1b1b1;
}
.modal-input-btn {
  width: 80%;
  height: 2rem;
  background-color: #1B3C33;
  outline: none;
  border-radius: 2px;
  color: white;
  border: none;
  font-size: 1rem;
}
.modal-input-btn:hover {
  cursor:pointer;
  background-color: #fff;
  transition: all 0.4s ease-out;
  color: #1B3C33;
}
.modal-input-login {
  font-size: 0.8rem;
  margin-top: 1rem;
  color: var(--lightgray);
  width: 80%;
  text-align: center;
}
@media screen and (max-width: 820px) {
  .modal-content-left{
    display: none;
  }
  .modal-content {
    grid-template-columns: auto;
    margin: 15% auto;
    width: 70%;
    height: 75%;
  }
}
@media screen and (max-width: 600px) {
  .modal-content {
    margin: 20% auto;
    width: 90%;
    height: 80%;
  }
}
</style>

