



<!-- 필요없는 vue -->



<template>
  <div>
    <!-- Button trigger modal -->
    <!-- <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#signup-modal">
      Signup
    </button> -->

    <!-- Modal -->
    <div class="modal fade" id="signup-modal" tabindex="-1" aria-labelledby="signup-modal-label" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="signup-modal-label">Signup</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="close"></button>
          </div>
          <div class="modal-body">
            <form class="row" @submit="signup">
              <div class="col-10 offset-1 col-md-8 offset-md-2 col-xxl-6 offset-xxl-3">
                <div class="mb-3">
                  <label for="username">ID</label>
                  <div>
                    <!-- ID form input -->
                    <input type="text" class="form-control" id="username" v-model="username">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="usernickname">Nickname</label>
                  <div>
                    <!-- Nickname form input -->
                    <input type="text" class="form-control" id="usernickname" v-model="userNickname">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="email">Email</label>
                  <div>
                    <!-- Email form input -->
                    <input type="text" class="form-control" id="email" v-model="email">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="password">Password</label>
                  <div>
                    <!-- PW form input -->
                    <input type="password" id="password1" class="form-control" v-model="password" />
                  </div>
                </div>
                <div class="mb-3">
                  <label for="passwordcheck">Password Check</label>
                  <div>
                    <!-- PW Check form input -->
                    <input type="password" id="passwordcheck" class="form-control" v-model="passwordCheck" />
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary">SignUp</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

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
    signup(event) {
      event.preventDefault()
      const signupData = {
        username: this.username,
        usernickname: this.userNickname,
        email: this.email,
        password: this.password,
      }
      // axios.post('http://127.0.0.1:8000/accounts/', signupData)
      //   .then(response => {
      //     console.log(response)
      //     this.$router.push({ name: 'Login' })
      //   })
    }
  }
}
</script>

<style>
</style>
