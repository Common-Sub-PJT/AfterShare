<template>
  <div>
    <!-- Button trigger modal -->
    <!-- <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#signup-modal">
      Signup
    </button> -->
    <div class="modal" id="email-modal">
      <div class="modal-content">
        <span class="close-btn">&times;</span>
        <div class="modal-content-left">
          <img src="@/assets/signup_login.svg" alt="모달이미지">
        </div>
        <div class="modal-content-right">
          <form action="/" method="GET" class="modal-form" id="form">
            <h1>Get stared with us today! Create your account by filling out the information below.</h1>
            <div class="form-validation">
              <input type="text" class="modal-input" id="name" name="name" placeholder="Enter your name">
              <p>Error Message</p>
            </div>
            <div class="form-validation">
              <input type="email" class="modal-input" id="email" name="email" placeholder="Enter your e-mail">
              <p>Error Message</p>
            </div>
            <div class="form-validation">
              <input type="text" class="modal-input" id="nickname" name="nickname" placeholder="Enter your nickname">
              <p>Error Message</p>
            </div>
            <div class="form-validation">
              <input type="password" class="modal-input" id="password" name="password" placeholder="Enter your password">
              <p>Error Message</p>
            </div>
            <div class="form-validation">
              <input type="password" class="modal-input" id="password-confirm" name="password" placeholder="Confirm your password">
              <p>Error Message</p>
            </div>
            <input type="submit" class="modal-input-btn" value="Sign up">
            <router-view to="/login"><span class="modal-input-login">Already have an account? Login </span></router-view>
          </form>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
const modal = document.getElementById('email-modal')
// const openBtn = document.querySelector('.main-btn') 이전에 만든 사인업 버튼
const closeBtn = document.querySelector('.close-btn')

openBtn.addEventListener('click', () => {
  modal.style.display='block'
})
closeBtn.addEventListener('click', () => {
  modal.style.display = 'none'
})
window.addEventListener('click', () =>{
  if(e.target === modal){
    modal.style
  }
})

export default {
  name: 'SignupView',
  components: {
  },
  data() {
    return {
      username: '',
      userNickname: '',
      email: '',
      password: '',
      passwordCheck: '',
    }
  },
  methods: {
    }
}

</script>

<style scoped>
:root {
  --errorColor: #f00e0e;
  --validColor: #0add0a;
}
.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
  display: none;
}
.modal-content{
  background: #f4f4f4;
  margin: 10% auto;
  width: 60%;
  box-shadow: 0 5px 8px 0 rgba(0, 0, 0, 0.2), 0 7px 20px 0 rgba(0, 0, 0, 0.2);

}
</style>
