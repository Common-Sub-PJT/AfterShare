<template>
  <div class="modal" id="email-modal">
    <div class="modal-content">
      <div class="modal-content-left">
        <img  id="modal-img" src="@/assets/test.png" alt="모달이미지">
      </div>
      <div class="modal-content-right">
        <div  @click="$emit('loginClose')" class="close-btn">&times;</div>
        <form  @submit.prevent="[login.login(credential), $emit('loginClose')]" class="modal-form" id="form">
          <h1 class="font-weight-bold">Login</h1>
          <div class="form-validation">
            <input v-model="credential.id" type="text" class="modal-input" id="name" name="name" placeholder="Enter your ID">
            <p>Error Message</p>
          </div>
          <div class="form-validation">
            <input v-model="credential.password" type="password" class="modal-input" id="password1" name="password" placeholder="Enter your password">
            <p>Error Message</p>
          </div>
          <input type="submit" class="modal-input-btn" value="Login">
          <div @click="[$emit('showSignup'), $emit('loginClose')]" style="border: none; cursor: pointer;" class="my-2">Sign Up</div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { useAccounts } from "@/stores/accounts";

export default {
  name: 'LoginView',
  components: {
  },
  data() {
    return {
      credential: {
        id: "",
        password: "",
      }
    }
  },
  setup(){
    const login = useAccounts()
    return{
      login
    }
  },
  methods: {

  }
}

</script>

<style scoped>
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
  min-height: 400px;
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
  font-size: 1.5rem;
  text-align: start;
  width: 80%;
  margin-bottom: 1rem;
}
.form-validation {
  margin-bottom: 0.5rem;
  width: 80%;
}
.form-validation.error {
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
}
.form-validation.error p {
  display: block;
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
@media screen and (max-width: 820px) {
  .modal-content-left{
    display: none;
  }
  .modal-content {
    grid-template-columns: auto;
    margin: 15% auto;
    width: 70%;
    height: 75%;
    max-height: 400px;
  }
}
@media screen and (max-width: 600px) {
  .modal-content {
    margin: 20% auto;
    width: 90%;
    height: 80%;
    min-height: 400px;
  }
}
</style>

