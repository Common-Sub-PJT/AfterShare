<template>
  <div class="modal" id="email-modal">text-deco
    <div class="modal-content">
      <div class="modal-content-left">
        <img  id="modal-img" src="@/assets/test.png" alt="모달이미지">
      </div>
      <div class="modal-content-right">
        <div  @click="$emit('passwordChangeClose')" class="close-btn">&times;</div>
        <!-- <form id="form" @submit.prevent="[passwordChangeValid(), $emit('passwordChangeClose'), $emit('showLogin')]" class="modal-form"> -->
        <form id="form" @submit.prevent="passwordChangeValid" class="modal-form">
          <h1 class="font-weight-bold">비밀번호 변경</h1>
          <div class="form-validation">
            <input v-model="password" @keyup="pwValid" type="password" class="modal-input" id="password" name="password" placeholder="Enter your password">
            <p class="password-check">Password Valid Error</p>
          </div>
          <div class="form-validation">
            <input v-model="newPassword" @keyup="newPwValid(), newPwCheckValid()" type="password" class="modal-input" id="newPassword" name="newPassword" placeholder="Enter your newPassword">
            <p class="newPassword-check"> New Password Valid Error</p>
          </div>
          <div class="form-validation">
            <input v-model="newPasswordCheck" @keyup="newPwCheckValid" type="password" class="modal-input" id="newPassword-confirm" name="newPassword" placeholder="Confirm your newPassword">
            <p class="newPasswordCheck-check">New Password-Check Valid Error</p>
          </div>
          <input type="submit" class="modal-input-btn" value="비밀번호 변경">
          <div @click="[$emit('passwordChangeClose')]" style="border: none; cursor: pointer;" class="my-2">취소</div>
        </form>
      </div>
    </div>
  </div> 
</template>

<script>
import { defineComponent } from "vue";
import axios from 'axios'
import secosi from '@/api/secosi.js'

export default defineComponent ({
  name: 'passwordChangeView',
  data() {
    return {
      errors:[],
      userId: '',
      email: '',
      password: '',
      newPassword: '',
      newPasswordCheck: '',
      passwordChangePermission: false,
    }
  },
  computed: {
  },
  setup() {
    const passwordChangeValid = function passwordChangeValid() {
      const valueCheck = this.password.length * this.newPassword.length
      if (valueCheck > 0 && this.passwordChangePermission) {
        const config = {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        }
        const context = {
          "current_password": this.password,
          "new_password": this.newPassword,
        }
        axios.patch(secosi.mypages.passwordChange(), context, config)
        .then(res => {
          console.log(res)
        })
        this.password = ''
        this.newPassword = ''
        this.newPasswordCheck = ''
        this.$emit('passwordChangeClose')
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
        this.passwordChangePermission &&= false
      } else {
        password.style.display = 'none'
        this.passwordChangePermission ||= true
      }
    }
    const newPwValid = function newPasswordValid() {
      const newPassword = document.querySelector('.newPassword-check')
      const pattern1 = /[0-9]/
	    const pattern2 = /[a-zA-Z]/
	    const pattern3 = /[~!@#$%^&*()_+|<>?:{}]/
      if (!pattern1.test(this.newPassword) || !pattern2.test(this.newPassword) || !pattern3.test(this.newPassword) || this.newPassword.length < 8) {
        newPassword.style.display = 'block'
        newPassword.innerText = '비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성되어야 합니다'
        this.passwordChangePermission &&= false
      } else {
        newPassword.style.display = 'none'
        this.passwordChangePermission ||= true
      }
    }
    const newPwCheckValid = function newPasswordCheckValid() {
      const newPasswordCheck = document.querySelector('.newPasswordCheck-check')
      if (this.newPassword === this.newPasswordCheck) {
        newPasswordCheck.style.display = 'none'
        this.passwordChangePermission ||= true
      } else {
        newPasswordCheck.style.display = 'block'
        newPasswordCheck.innerText = '비밀번호와 일치하지 않습니다'
        this.passwordChangePermission &&= false
      }
    }

    return{
      passwordChangeValid,
      pwValid,
      newPwValid,
      newPwCheckValid,
    }
  }
})
</script>

<style scoped>
.userId-check, .email-check, .password-check, .newPassword-check , .newPasswordCheck-check{
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
@media screen and (max-width: 1900px) {
  .modal-content-left{
    display: none;
  }
  .modal-content {
    grid-template-columns: auto;
    margin: 15% auto;
    width: 30%;
    height: 55%;
  }
}
@media screen and (max-width: 820px) {
  .modal-content-left{
    display: none;
  }
  .modal-content {
    grid-template-columns: auto;
    margin: 15% auto;
    width: 60%;
    height: 55%;
  }
}
@media screen and (max-width: 600px) {
  .modal-content-left{
    display: none;
  }
  .modal-content {
    grid-template-columns: auto;
    margin: 20% auto;
    width: 90%;
    height: 55%;
  }
}
</style>

