<template>
  <SignupModal></SignupModal>

  <div>
    <div class="modal fade" id="login-modal" tabindex="-1" aria-labelledby="login-modal-label" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="login-modal-label">Login</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form class="row" @submit="login">
              <div class="col-10 offset-1 col-md-8 offset-md-2 col-xxl-6 offset-xxl-3">
                <div class="mb-3">
                  <label for="username">ID</label>
                  <div>
                    <!-- ID form input -->
                    <input type="text" class="form-control" id="username" v-model="username">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="password">Password</label>
                  <div>
                    <!-- PW form input -->
                    <input type="password" id="password" class="form-control" v-model="password" />
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Login</button>
                <SignupButton></SignupButton>
                <SignupUrl></SignupUrl>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import SignupButton from '@/views/accounts/components/SignupButton.vue'
import SignupUrl from '@/views/accounts/components/SignupUrl.vue'
import SignupModal from '@/views/accounts/components/SignupModal.vue'

export default {
  name: 'LoginModal',
  components: {
    SignupButton,
    SignupUrl,
    SignupModal,
  },
  data() {
    return {
      username: '',
      password: '',
    }
  },
  methods: {
    login(event) {
      event.preventDefault()
      const loginData = {
        username: this.username,
        password: this.password,
      }
      console.log(loginData)
      // axios.post('http://127.0.0.1:8000/accounts/login/', loginData)
      //   .then(response => {
      //     commit('SET_TOKEN', response.data.key)
      //     localStorage.setItem('token', response.data.key)
      //   })
    }
  }
}
</script>

<style>
</style>
