<template>
  <div>
    <h1 class="fw-bold m-3">Login</h1>
    <p>This is Login page</p>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal">
      Login Button
    </button>
    <SignupView></SignupView>
    <!-- Modal -->
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="loginModalLabel">Login</h5>
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
                <button type="button" class="btn btn-primary">Login</button>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#SignupModal">
                  Signup
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SignupView from '@/views/accounts/SignupView.vue'

export default {
  name: 'LoginView',
  components: {
    SignupView,
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
      const LoginData = {
        username: this.username,
        password: this.password,
      }
      // axios.post('http://127.0.0.1:8000/accounts/login/', LoginData)
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
